package com.andriawan.sistempenilaianmahasiswa.data.usecase.students

import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.DeleteStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DeleteStudentUseCaseImpl(
    private val studentsRepository: StudentsRepository,
    private val gradesRepository: GradesRepository,
    private val errorHandler: ErrorHandler
): DeleteStudentUseCase {

    override fun execute(params: Param): Flow<Response<Nothing>> = flow {
        emit(Response.Loading)
        try {
            studentsRepository.deleteStudent(params.studentId)
            gradesRepository.deleteGrade(studentId = params.studentId)
            emit(Response.Success())
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val studentId: String,
    )
}