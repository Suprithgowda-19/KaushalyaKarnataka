package com.example.kaushalyakarnataka.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaushalyakarnataka.data.FirestoreRepository
import com.example.kaushalyakarnataka.model.Review
import com.example.kaushalyakarnataka.model.ServiceItem
import com.example.kaushalyakarnataka.model.Worker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkerViewModel : ViewModel() {
    private val repository = FirestoreRepository()

    val workers: StateFlow<List<Worker>> = repository.getWorkers()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedWorker = MutableStateFlow<Worker?>(null)
    val selectedWorker: StateFlow<Worker?> = _selectedWorker.asStateFlow()

    fun selectWorker(workerId: String) {
        viewModelScope.launch {
            _selectedWorker.value = repository.getWorkerById(workerId)
        }
    }

    fun saveWorker(worker: Worker) {
        viewModelScope.launch {
            repository.saveWorker(worker)
        }
    }

    fun addService(workerId: String, name: String, price: String) {
        viewModelScope.launch {
            repository.addService(workerId, ServiceItem(name = name, price = price))
            selectWorker(workerId)
        }
    }

    fun updateService(workerId: String, service: ServiceItem) {
        viewModelScope.launch {
            repository.updateService(workerId, service)
            selectWorker(workerId)
        }
    }

    fun addReview(workerId: String, reviewerName: String, comment: String, rating: Double) {
        viewModelScope.launch {
            repository.addReview(workerId, Review(reviewerName = reviewerName, comment = comment, rating = rating))
            selectWorker(workerId)
        }
    }

    fun uploadPortfolioImage(workerId: String, uri: Uri) {
        viewModelScope.launch {
            repository.uploadPortfolioImage(workerId, uri)
            selectWorker(workerId)
        }
    }
    
    fun initDemoData() {
        viewModelScope.launch {
            if (workers.value.isEmpty()) {
                val demoWorkers = listOf(
                    Worker(
                        id = "demo_electrician_1",
                        name = "Bagalkot Electricals",
                        title = "Master Electrician",
                        category = "Electrician",
                        location = "Bagalkot",
                        phone = "9876543210",
                        bio = "Professional home wiring and repair specialist.",
                        rating = 4.9,
                        reviewCount = 1,
                        services = listOf(
                            ServiceItem(name = "Switch Fix", price = "₹150"),
                            ServiceItem(name = "Main Board Repair", price = "₹500")
                        ),
                        imageUrl = "https://images.unsplash.com/photo-1621905252507-b35492cc74b4",
                        reviews = listOf(Review(reviewerName = "Ramesh", comment = "Great service!", rating = 5.0))
                    ),
                    Worker(
                        id = "demo_plumber_1",
                        name = "Pro Plumbers",
                        title = "Expert Leakage Fixer",
                        category = "Plumber",
                        location = "Bagalkot",
                        phone = "8877665544",
                        bio = "Quick and reliable plumbing services for your home.",
                        rating = 4.7,
                        reviewCount = 1,
                        services = listOf(
                            ServiceItem(name = "Tap Leak Fix", price = "₹100"),
                            ServiceItem(name = "Pipe Fitting", price = "₹300")
                        ),
                        imageUrl = "https://images.unsplash.com/photo-1581244277943-fe4a9c777189",
                        reviews = listOf(Review(reviewerName = "Amit", comment = "Prompt arrival.", rating = 4.5))
                    ),
                    Worker(
                        id = "demo_carpenter_1",
                        name = "Wood Art Karnataka",
                        title = "Custom Furniture Expert",
                        category = "Carpenter",
                        location = "Bagalkot",
                        phone = "7766554433",
                        bio = "Handcrafted furniture and home repair services.",
                        rating = 4.8,
                        reviewCount = 1,
                        services = listOf(
                            ServiceItem(name = "Door Lock Fix", price = "₹200"),
                            ServiceItem(name = "Table Polish", price = "₹600")
                        ),
                        imageUrl = "https://images.unsplash.com/photo-1522202176988-66273c2fd55f",
                        reviews = listOf(Review(reviewerName = "Sneha", comment = "Beautiful work.", rating = 5.0))
                    )
                )
                demoWorkers.forEach { repository.saveWorker(it) }
            }
        }
    }
}
