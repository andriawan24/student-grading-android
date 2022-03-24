package com.andriawan.sistempenilaianmahasiswa.domain.usecase

import com.andriawan.sistempenilaianmahasiswa.data.usecase.SignInEmailUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface SignInEmailUseCase: FlowUseCase<SignInEmailUseCaseImpl.Param, String>