package com.example.kotlinmultiplatformspike

import org.koin.core.module.Module
import org.koin.dsl.module

// // We create here the Android modules
actual val platformModule: Module = module {

    single {
        CorroutineDispatcherProvider()
    }

    single {
        DatabaseDriverFactory(get())
    }

    single {
        BeerRepository(get(), get())
    }
    single {
        BeersListViewModel(get(), get())
    }

}