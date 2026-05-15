package com.example.kaushalyakarnataka.data

import android.net.Uri
import com.example.kaushalyakarnataka.model.Review
import com.example.kaushalyakarnataka.model.ServiceItem
import com.example.kaushalyakarnataka.model.Worker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirestoreRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val workersCollection = firestore.collection("workers")

    fun getWorkers(): Flow<List<Worker>> = callbackFlow {
        val subscription = workersCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val workers = snapshot.toObjects(Worker::class.java)
                trySend(workers)
            }
        }
        awaitClose { subscription.remove() }
    }

    suspend fun getWorkerById(workerId: String): Worker? {
        return try {
            workersCollection.document(workerId).get().await().toObject(Worker::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveWorker(worker: Worker) {
        if (worker.id.isEmpty()) {
            val docRef = workersCollection.document()
            workersCollection.document(docRef.id).set(worker.copy(id = docRef.id)).await()
        } else {
            workersCollection.document(worker.id).set(worker).await()
        }
    }

    suspend fun addService(workerId: String, service: ServiceItem) {
        val worker = getWorkerById(workerId) ?: return
        val updatedServices = worker.services + service.copy(id = UUID.randomUUID().toString())
        workersCollection.document(workerId).update("services", updatedServices).await()
    }

    suspend fun updateService(workerId: String, service: ServiceItem) {
        val worker = getWorkerById(workerId) ?: return
        val updatedServices = worker.services.map { if (it.id == service.id) service else it }
        workersCollection.document(workerId).update("services", updatedServices).await()
    }

    suspend fun addReview(workerId: String, review: Review) {
        val worker = getWorkerById(workerId) ?: return
        val updatedReviews = worker.reviews + review.copy(id = UUID.randomUUID().toString())
        val newRating = updatedReviews.map { it.rating }.average()
        workersCollection.document(workerId).update(
            "reviews", updatedReviews,
            "rating", newRating,
            "reviewCount", updatedReviews.size
        ).await()
    }

    suspend fun uploadPortfolioImage(workerId: String, imageUri: Uri): String {
        val fileName = UUID.randomUUID().toString()
        val ref = storage.reference.child("portfolio/$workerId/$fileName")
        ref.putFile(imageUri).await()
        val downloadUrl = ref.downloadUrl.await().toString()
        
        val worker = getWorkerById(workerId) ?: throw Exception("Worker not found")
        val updatedPortfolio = worker.portfolioImages + downloadUrl
        workersCollection.document(workerId).update("portfolioImages", updatedPortfolio).await()
        
        return downloadUrl
    }
}