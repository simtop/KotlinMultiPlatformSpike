package com.example.kotlinmultiplatformspike

import comexamplekotlinmultiplaformspike.db.Beer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

object BeerMapper {

    fun fromListBeerApiModelToBeerModel(
        beers: List<BeersApiResponseItem>
    ): List<BeerModel> {
        return beers.map { fromBeerApiModelToBeerModel(it) }
    }

    fun fromBeerApiModelToBeerModel(
        beer: BeersApiResponseItem
    ): BeerModel {
        with(beer) {
            return BeerModel(
                id ?: 0,
                name ?: "",
                tagline ?: "",
                description ?: "",
                imageUrl ?: "",
                abv ?: 0.0,
                ibu ?: 0.0,
                foodPairing ?: listOf(),
                false
            )
        }
    }

    fun fromBeerDbModelToBeer(
        id: Int,
        name: String,
        tagline: String,
        description: String,
        imageUrl: String,
        abv: Double,
        ibu: Double,
        foodPairing: String,
        availability: Boolean
    ): BeerModel {
        val serializer = Json(from = Json) { ignoreUnknownKeys = true }

        return BeerModel(
            id,
            name,
            tagline,
            description,
            imageUrl,
            abv,
            ibu,
            serializer.decodeFromString(ListSerializer(String.serializer()), foodPairing),
            availability
        )
    }

    fun fromBeerModelToBeerDbModel(beer: BeerModel): Beer {
        val serializer = Json(from = Json) { ignoreUnknownKeys = true }

        return Beer(
            id = beer.id,
            name = beer.name,
            tagline = beer.tagline,
            description = beer.description,
            imageUrl = beer.imageUrl,
            abv = beer.abv,
            ibu = beer.ibu,
            foodPairing = serializer.encodeToString(
                ListSerializer(String.serializer()),
                beer.foodPairing
            ),
            availability = beer.availability)
    }
}