package com.andriawan.sistempenilaianmahasiswa.utils.usecase

interface SuspendUseCase<in Params, out T> {
    suspend fun execute(params: Params): T
}