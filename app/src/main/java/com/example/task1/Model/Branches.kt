package com.example.task1.Model

import kotlinx.serialization.Serializable


@Serializable
data class Branches(
    val id: String,
    val address: String,
    val image:String?
)

