package com.andriawan.sistempenilaianmahasiswa.domain.usecase.students

import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.EditStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface EditStudentUseCase: FlowUseCase<EditStudentUseCaseImpl.Param, Nothing>