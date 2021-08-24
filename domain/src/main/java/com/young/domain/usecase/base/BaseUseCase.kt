package com.young.domain.usecase.base

interface BaseUseCase<in Param , out Result> {
    suspend operator fun invoke(param : Param) : Result
}