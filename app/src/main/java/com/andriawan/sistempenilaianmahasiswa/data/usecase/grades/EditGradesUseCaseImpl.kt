package com.andriawan.sistempenilaianmahasiswa.data.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.EditGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EditGradesUseCaseImpl(
    private val gradesRepository: GradesRepository,
    private val errorHandler: ErrorHandler
) : EditGradesUseCase{

    override fun execute(params: Param): Flow<Response<Nothing>> = flow {
        emit(Response.Loading)

        try {
            gradesRepository.updateGrade(params.grades, params.isUpdateAll)
            emit(Response.Success())
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val grades: List<Grades>,
        val isUpdateAll: Boolean = false
    )
}