package com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.CreateGradeUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface CreateGradeUseCase : FlowUseCase<CreateGradeUseCaseImpl.Param, Nothing>