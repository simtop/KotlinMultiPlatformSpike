package com.example.kotlinmultiplatformspike

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class BeersListViewModel(private val repository: BeerRepository, private val dispatcherProvider: CorroutineDispatcherProvider): ViewModel() {

    var beerList =
        MutableLiveData<List<BeerModel>>(emptyList())

    fun getBeerList(page: Int = 1) {
        viewModelScope.launch(dispatcherProvider.io) {
            val list = repository.getBeers(false, page)
            beerList.postValue(list)
        }
    }
}