package com.example.task1.Model

import kotlinx.serialization.Serializable

@Serializable
data class Clients(
    val client_id: String,
    val name: String,
    val surname:String,
    /*    val email:String,
        val password:String*/
)
