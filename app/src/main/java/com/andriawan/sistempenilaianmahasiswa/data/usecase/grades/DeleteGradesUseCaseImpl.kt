package com.andriawan.sistempenilaianmahasiswa.data.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.DeleteGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteGradesUseCaseImpl(
    private val gradesRepository: GradesRepository,
    private val errorHandler: ErrorHandler
): DeleteGradesUseCase {

    override fun execute(params: Param): Flow<Response<Nothing>> = flow {
        emit(Response.Loading)

        try {
            gradesRepository.deleteGrade(studentId = params.studentId, subject = params.subject)

            emit(Response.Success())
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val studentId: String,
        val subject: String
    )
}