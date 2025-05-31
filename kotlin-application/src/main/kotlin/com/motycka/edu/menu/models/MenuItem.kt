package com.motycka.edu.menu.models

import kotlinx.serialization.Serializable

@Serializable
data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double
)
