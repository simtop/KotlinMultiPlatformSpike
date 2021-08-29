package com.example.kotlinmultiplatformspike

import kotlinx.coroutines.CoroutineDispatcher


expect class CorroutineDispatcherProvider {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}