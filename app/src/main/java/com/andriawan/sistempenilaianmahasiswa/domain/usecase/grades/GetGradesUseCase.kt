package com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.GetGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.utils.usecase.FlowUseCase

interface GetGradesUseCase: FlowUseCase<GetGradesUseCaseImpl.Param, List<Grades>>