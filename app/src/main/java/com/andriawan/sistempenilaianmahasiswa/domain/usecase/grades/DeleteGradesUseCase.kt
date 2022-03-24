package com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.DeleteGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface DeleteGradesUseCase : FlowUseCase<DeleteGradesUseCaseImpl.Param, Nothing>