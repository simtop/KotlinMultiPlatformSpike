package com.example.kotlinmultiplatformspike

class BeerRepository(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = KtorRemote()

    suspend fun getBeers(forceReload: Boolean, page: Int): Either<Exception, List<BeerModel>> {
        return try {
            val cachedBeers = database.getAllBeers()
            if (cachedBeers.isNotEmpty() && !forceReload) {
                Either.Right(cachedBeers)
            } else {
                val test = fromListBeerApiModelToBeerModel(api.getListOfBeers(page)).also { beers ->
                    database.clearDatabase()
                    database.insertBeerList(beers)
                }
                Either.Right(test)
            }
        } catch (exception: Exception) {
            Either.Left(exception)
        }
    }

    private fun fromListBeerApiModelToBeerModel(
        beers: List<BeersApiResponseItem>
    ): List<BeerModel> {
        return beers.map { fromBeerApiModelToBeerModel(it) }
    }


    private fun fromBeerApiModelToBeerModel(
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

}