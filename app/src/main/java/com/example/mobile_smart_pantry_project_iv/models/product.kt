package com.example.mobile_smart_pantry_project_iv.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val uuid: Int,
    val name: String,
    var quantity: Int,
    val category: String,
    val imageRef: String
)
