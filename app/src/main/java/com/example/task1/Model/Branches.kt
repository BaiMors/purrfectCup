package com.example.task1.Model

import kotlinx.serialization.Serializable


@Serializable
data class Branches(
    val id: Int,
    val address: String,
    val image:String?
)

