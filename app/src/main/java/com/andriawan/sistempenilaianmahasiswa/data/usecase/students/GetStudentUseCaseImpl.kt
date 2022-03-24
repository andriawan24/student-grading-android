package com.andriawan.sistempenilaianmahasiswa.data.usecase.students

import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.GetStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetStudentUseCaseImpl(
    private val studentsRepository: StudentsRepository,
    private val errorHandler: ErrorHandler
): GetStudentUseCase {
    override fun execute(params: Param): Flow<Response<Student>> = flow {
        emit(Response.Loading)

        try {
            val student = studentsRepository.getStudent(params.studentId)

            emit(Response.Success(student))
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val studentId: String
    )
}