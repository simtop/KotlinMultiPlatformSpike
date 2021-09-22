package com.example.kotlinmultiplatformspike

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class BeersListViewModel(private val repository: BeerRepository, private val dispatcherProvider: CorroutineDispatcherProvider): ViewModel() {

    private val _beerList = MutableLiveData<List<BeerModel>>(emptyList())
    val beerList = _beerList

    fun getBeerList(page: Int = 1) {
        viewModelScope.launch(dispatcherProvider.io) {
            repository.getBeers(false, page).also(::treatGetBeerResult)
        }
    }

    private fun treatGetBeerResult(result: Either<Exception, List<BeerModel>>) {
            when(result){
                is Either.Left -> {
                    println("${result.value.message}")
                }
                is Either.Right -> _beerList.postValue(result.value)
            }
    }
}