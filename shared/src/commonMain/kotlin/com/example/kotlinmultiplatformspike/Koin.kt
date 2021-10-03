package com.example.kotlinmultiplatformspike

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            platformModule,
            coreModule
        )
    }

    // Dummy initialization logic, making use of appModule declarations for demonstration purposes.
    val koin = koinApplication.koin
    // doOnStartup is a lambda which is implemented in Swift on iOS side
    val doOnStartup = koin.get<() -> Unit>()
    doOnStartup.invoke()

    return koinApplication
}

private val coreModule = module {

    single {
        Database(
            get()
        )
    }

    single<BeerService> {
        KtorRemote()
    }

}

internal inline fun <reified T> Scope.getWith(vararg params: Any?): T {
    return get(parameters = { parametersOf(*params) })
}

expect val platformModule: Module