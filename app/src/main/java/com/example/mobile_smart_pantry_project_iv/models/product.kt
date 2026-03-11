package com.example.mobile_smart_pantry_project_iv.models

data class Product(
    val uuid: Int,
    val name: String,
    val quantity: Int,
    val category: String,
    val imageRef: String
)
