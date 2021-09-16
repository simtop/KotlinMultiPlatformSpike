package com.example.kotlinmultiplatformspike

data class BeerModel(
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val imageUrl: String,
    val abv: Double,
    val ibu: Double,
    val foodPairing: List<String>,
    var availability: Boolean = true
) {
    companion object {
        val empty = BeerModel(
            1,
            "",
            "",
            "",
            "",
            0.0,
            0.0,
            listOf(),
            true
        )
    }
}