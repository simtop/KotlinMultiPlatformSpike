package com.example.kotlinmultiplatformspike

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class CorroutineDispatcherProvider {
    actual val main: CoroutineDispatcher
        get() = Dispatchers.Main
    actual val default: CoroutineDispatcher
        get() = Dispatchers.Default
    actual val io: CoroutineDispatcher
        get() = Dispatchers.IO
    actual val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}