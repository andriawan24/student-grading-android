package com.andriawan.sistempenilaianmahasiswa.domain.usecase.students

import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.GetStudentsUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface GetStudentsUseCase: FlowUseCase<GetStudentsUseCaseImpl.Param, List<Student>?>