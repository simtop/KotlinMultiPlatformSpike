package com.example.kotlinmultiplatformspike

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BeersApiResponseItem(
    val id: Int?,
    val name: String?,
    val tagline: String?,
    val description: String?,
    @SerialName("image_url")
    val imageUrl: String?,
    val abv: Double?,
    val ibu: Double?,
    @SerialName("food_pairing")
    val foodPairing: List<String>?
)
