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