package com.andriawan.sistempenilaianmahasiswa.data.usecase.students

import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.EditStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class EditStudentUseCaseImpl(
    private val studentsRepository: StudentsRepository,
    private val errorHandler: ErrorHandler
): EditStudentUseCase {

    override fun execute(params: Param): Flow<Response<Nothing>> = flow {
        emit(Response.Loading)

        try {
            studentsRepository.editStudent(params.student)

            emit(Response.Success())
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val student: Student
    )
}