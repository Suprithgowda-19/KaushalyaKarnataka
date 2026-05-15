package com.example.kaushalyakarnataka.model

data class Worker(
    val id: String = "",
    val name: String = "",
    val title: String = "",
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val imageUrl: String = "",
    val category: String = "",
    val location: String = "",
    val phone: String = "",
    val bio: String = "",
    val services: List<ServiceItem> = emptyList(),
    val portfolioImages: List<String> = emptyList(),
    val reviews: List<Review> = emptyList()
)

data class ServiceItem(
    val id: String = "",
    val name: String = "",
    val price: String = ""
)

data class Review(
    val id: String = "",
    val reviewerName: String = "",
    val comment: String = "",
    val rating: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)