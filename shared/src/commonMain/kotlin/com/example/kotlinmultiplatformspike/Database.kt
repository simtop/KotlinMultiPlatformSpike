package com.example.kotlinmultiplatformspike

import com.simtop.shared_db.beers.AppDatabase
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries


    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllBeers()
        }
    }

    internal fun getAllBeers(): List<BeerModel> {
        return dbQuery.selectAllBeers(::fromBeerDbModelToBeer).executeAsList()
    }

    private fun fromBeerDbModelToBeer(
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

    internal fun insertBeer(beer: BeerModel) {
        val serializer = Json(from = Json) { ignoreUnknownKeys = true }

        dbQuery.insertBeer(
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
            availability = beer.availability
        )
    }
}