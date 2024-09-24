package com.example.task1.Model

import kotlinx.serialization.Serializable

@Serializable
data class Carousel(
    val id: String,
    val image:String?
)
