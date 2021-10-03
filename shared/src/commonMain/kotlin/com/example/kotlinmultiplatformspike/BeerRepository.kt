package com.example.kotlinmultiplatformspike

class BeerRepository(private val database: Database, private val api: BeerService) {

    suspend fun getBeers(forceReload: Boolean, page: Int): Either<Exception, List<BeerModel>> {
        return try {
            val cachedBeers = database.getAllBeers()
            if (cachedBeers.isNotEmpty() && !forceReload) {
                Either.Right(cachedBeers)
            } else {
                val test = BeerMapper.fromListBeerApiModelToBeerModel(api.getListOfBeers(page)).also { beers ->
                    database.clearDatabase()
                    database.insertBeerList(beers)
                }
                Either.Right(test)
            }
        } catch (exception: Exception) {
            Either.Left(exception)
        }
    }
}