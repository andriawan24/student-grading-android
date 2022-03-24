package com.andriawan.sistempenilaianmahasiswa.utils.usecase

import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in Params, out T>: UseCase<Params, Flow<Response<T>>> {
    override fun execute(params: Params): Flow<Response<T>>
}