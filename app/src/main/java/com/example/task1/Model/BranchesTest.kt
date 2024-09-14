package com.example.task1.Model

import kotlinx.serialization.Serializable


@Serializable
data class BranchesTest(
    val id: Int,
    val name: String,
    val image:String?
)

