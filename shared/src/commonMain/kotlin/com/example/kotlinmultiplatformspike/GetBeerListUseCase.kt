package com.example.kotlinmultiplatformspike

class GetBeerListUseCase(private val beerRepository: BeerRepository) :
    BaseUseCase<List<BeerModel>, GetBeerListUseCase.Params>() {

    inner class Params(val quantity: Int)

    override suspend fun buildUseCase(params: Params): Either<Exception, List<BeerModel>> =
        beerRepository.getBeers(false, params.quantity)

}