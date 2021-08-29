package com.example.kotlinmultiplatformspike

interface BeerService{
    suspend fun getListOfBeers(
        page : Int
    ): List<BeersApiResponseItem>
}