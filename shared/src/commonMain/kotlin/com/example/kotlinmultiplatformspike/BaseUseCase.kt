package com.example.kotlinmultiplatformspike

abstract class BaseUseCase<T, PARAMS> protected constructor() {

    protected abstract suspend fun buildUseCase(params: PARAMS): Either<Exception, T>

    suspend operator fun invoke(params: PARAMS) = buildUseCase(params)
}