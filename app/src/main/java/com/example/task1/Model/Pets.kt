package com.example.task1.Model

import kotlinx.serialization.Serializable

@Serializable
data class Pets(
    val id: String,
    val name: String,
    val description:String,
    val image:String?,
    val branch:String
)
