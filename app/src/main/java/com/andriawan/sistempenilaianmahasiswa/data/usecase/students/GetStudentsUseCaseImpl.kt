package com.andriawan.sistempenilaianmahasiswa.data.usecase.students

import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.GetStudentsUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class GetStudentsUseCaseImpl(
    private val studentsRepository: StudentsRepository,
    private val errorHandler: ErrorHandler
) : GetStudentsUseCase {

    override fun execute(params: Param): Flow<Response<List<Student>?>> = flow {
        emit(Response.Loading)

        try {
            var students = studentsRepository.getStudents(query = params.query)

            if (params.query != null) {
                Timber.d("Params ${params.query}")
                students = students.filter {
                    it.name!!.lowercase().contains(params.query.lowercase()) || it.nim!!.lowercase()
                        .contains(params.query.lowercase())
                }
            }

            emit(Response.Success(students))
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        val query: String? = null
    )
}