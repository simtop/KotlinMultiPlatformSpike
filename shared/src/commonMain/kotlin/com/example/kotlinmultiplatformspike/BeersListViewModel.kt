package com.example.kotlinmultiplatformspike

import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class BeersListViewModel(private val remote: BeerService, private val dispatcherProvider: CorroutineDispatcherProvider): ViewModel() {

    var beerList =
        MutableLiveData<List<BeersApiResponseItem>>(emptyList())

    fun getBeerList(page: Int = 1) {
        viewModelScope.launch(dispatcherProvider.io) {
            remote.getListOfBeers(page).also(::doSomething)
        }
    }

    private fun doSomething(list: List<BeersApiResponseItem>) {
        beerList.postValue(list)
    }
}