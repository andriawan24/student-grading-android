package com.andriawan.sistempenilaianmahasiswa.domain.usecase.students

import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.CreateStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface CreateStudentUseCase : FlowUseCase<CreateStudentUseCaseImpl.Param, Nothing>