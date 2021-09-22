package com.example.kotlinmultiplatformspike

import com.simtop.shared_db.beers.AppDatabase

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries


    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllBeers()
        }
    }

    internal fun getAllBeers(): List<BeerModel> {
        return dbQuery.selectAllBeers(::fromBeerDbValuesToBeer).executeAsList()
    }

    private fun fromBeerDbValuesToBeer(
        id: Int,
        name: String,
        tagline: String,
        description: String,
        imageUrl: String,
        abv: Double,
        ibu: Double,
        foodPairing: String,
        availability: Boolean
    ): BeerModel =
        BeerMapper.fromBeerDbModelToBeer(id, name, tagline, description, imageUrl, abv, ibu, foodPairing, availability)


    private fun insertBeer(beer: BeerModel) {

        val dbBeerModel = BeerMapper.fromBeerModelToBeerDbModel(beer)

        with(dbBeerModel) {
            dbQuery.insertBeer(
                id = id,
                name = name,
                tagline = tagline,
                description = description,
                imageUrl = imageUrl,
                abv = abv,
                ibu = ibu,
                foodPairing = foodPairing,
                availability = availability)
        }
    }

    fun insertBeerList(beers: List<BeerModel>) = database.transaction {
        beers.map {
            insertBeer(it)
        }
    }
}