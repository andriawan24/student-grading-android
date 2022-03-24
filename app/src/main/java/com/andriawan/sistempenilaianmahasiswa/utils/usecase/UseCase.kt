package com.andriawan.sistempenilaianmahasiswa.utils.usecase

interface UseCase<in Params, out T> {
    fun execute(params: Params): T
}