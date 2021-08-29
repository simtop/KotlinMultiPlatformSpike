package com.example.kotlinmultiplatformspike

import kotlinx.coroutines.CoroutineDispatcher

actual class CorroutineDispatcherProvider {
    actual val main: CoroutineDispatcher
        get() = TODO("Not yet implemented")
    actual val default: CoroutineDispatcher
        get() = TODO("Not yet implemented")
    actual val io: CoroutineDispatcher
        get() = TODO("Not yet implemented")
    actual val unconfined: CoroutineDispatcher
        get() = TODO("Not yet implemented")
}