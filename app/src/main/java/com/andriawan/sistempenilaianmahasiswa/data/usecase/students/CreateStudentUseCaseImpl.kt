package com.andriawan.sistempenilaianmahasiswa.data.usecase.students

import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.CreateStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreateStudentUseCaseImpl(
    private val studentsRepository: StudentsRepository,
    private val errorHandler: ErrorHandler
): CreateStudentUseCase {

    override fun execute(params: Param): Flow<Response<Nothing>> = flow {
        emit(Response.Loading)

        try {
            val student = Student(
                name = params.name,
                nim = params.nim,
                entryYear = params.entryYear,
                major = params.major,
                phoneNumber = ""
            )

            studentsRepository.createStudent(student)

            emit(Response.Success())
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val nim: String,
        val name: String,
        val entryYear: String,
        val major: String
    )
}