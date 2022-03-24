package com.andriawan.sistempenilaianmahasiswa.domain.usecase.students

import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.GetStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface GetStudentUseCase: FlowUseCase<GetStudentUseCaseImpl.Param, Student>