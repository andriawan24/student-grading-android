package com.andriawan.sistempenilaianmahasiswa.data.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.GetGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetGradesUseCaseImpl(
    private val gradesRepository: GradesRepository,
    private val errorHandler: ErrorHandler
) : GetGradesUseCase {

    override fun execute(params: Param): Flow<Response<List<Grades>>> = flow {
        emit(Response.Loading)

        try {
            val grades = gradesRepository.getGrades(
                student_id = params.studentId,
                type = params.type,
                subject = params.subject
            )

            emit(Response.Success(grades))
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val studentId: String,
        val type: String? = null,
        val subject: String? = null
    )
}