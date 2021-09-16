package com.example.kotlinmultiplatformspike

class BeerRepository(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = KtorRemote()

    @Throws(Exception::class) suspend fun getBeers(forceReload: Boolean, page: Int): List<BeerModel> {
        val cachedBeers = database.getAllBeers()
        return if (cachedBeers.isNotEmpty() && !forceReload) {
            cachedBeers
        } else {
            fromListBeerApiModelToBeerModel(api.getListOfBeers(page)).also { beers ->
                database.clearDatabase()
                database.insertBeerList(beers)
            }
        }
    }

    private fun fromListBeerApiModelToBeerModel(
        beers: List<BeersApiResponseItem>
    ): List<BeerModel>  {
        return beers.map { fromBeerApiModelToBeerModel(it) }
    }


    private fun fromBeerApiModelToBeerModel(
        beer: BeersApiResponseItem
    ) : BeerModel {
        with(beer) {
            return BeerModel(
                id ?: 0,
                name ?: "",
                tagline ?: "",
                description ?: "",
                imageUrl ?: "",
                abv ?: 0.0,
                ibu ?: 0.0,
                foodPairing?: listOf(),
                false
            )
        }
    }

}