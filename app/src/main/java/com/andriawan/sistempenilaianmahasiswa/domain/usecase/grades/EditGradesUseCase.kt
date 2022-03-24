package com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.EditGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface EditGradesUseCase : FlowUseCase<EditGradesUseCaseImpl.Param, Nothing>