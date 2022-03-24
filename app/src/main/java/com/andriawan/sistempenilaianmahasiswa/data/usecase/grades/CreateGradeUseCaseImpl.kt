package com.andriawan.sistempenilaianmahasiswa.data.usecase.grades

import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.CreateGradeUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.ui.detail.DetailFragment
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CreateGradeUseCaseImpl(
    private val gradesRepository: GradesRepository,
    private val errorHandler: ErrorHandler
): CreateGradeUseCase {

    override fun execute(params: Param): Flow<Response<Nothing>> = flow {
        emit(Response.Loading)

        try {
            val grades = listOf(
                Grades(
                    subject = params.subject,
                    teacherId = params.teacherId,
                    studentId = params.studentId,
                    grade = params.tasksGrade,
                    type = DetailFragment.TYPE_TASKS,
                    id = ""
                ),
                Grades(
                    subject = params.subject,
                    teacherId = params.teacherId,
                    studentId = params.studentId,
                    grade = params.quizGrade,
                    type = DetailFragment.TYPE_QUIZ,
                    id = ""
                ),
                Grades(
                    subject = params.subject,
                    teacherId = params.teacherId,
                    studentId = params.studentId,
                    grade = params.utsGrade,
                    type = DetailFragment.TYPE_UTS,
                    id = ""
                ),
                Grades(
                    subject = params.subject,
                    teacherId = params.teacherId,
                    studentId = params.studentId,
                    grade = params.uasGrade,
                    type = DetailFragment.TYPE_UAS,
                    id = ""
                ),
            )

            grades.forEach {
                gradesRepository.createGrades(it)
            }

            emit(Response.Success())
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        }

    }.flowOn(Dispatchers.IO)

    data class Param(
        val studentId: String,
        val teacherId: String,
        val subject: String,
        val tasksGrade: Number,
        val quizGrade: Number,
        val utsGrade: Number,
        val uasGrade: Number
    )
}