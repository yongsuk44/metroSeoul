package com.young.domain.usecase

interface BaseUseCase<in Param , out Result> {
    suspend operator fun invoke(param : Param) : Result
}