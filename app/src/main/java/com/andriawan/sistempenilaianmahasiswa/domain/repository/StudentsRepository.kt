package com.andriawan.sistempenilaianmahasiswa.domain.repository

import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import kotlin.jvm.Throws

interface StudentsRepository {

    @Throws(FirebaseFirestoreException::class)
    suspend fun getStudents(
        query: String? = null,
        source: Source = Source.DEFAULT
    ): List<Student>

    @Throws(FirebaseFirestoreException::class)
    suspend fun getStudent(
        studentId: String,
        source: Source = Source.DEFAULT
    ): Student?

    @Throws(FirebaseFirestoreException::class)
    suspend fun createStudent(
        student: Student
    )

    @Throws(FirebaseFirestoreException::class)
    suspend fun editStudent(
        student: Student
    )

    @Throws(FirebaseFirestoreException::class)
    suspend fun deleteStudent(
        studentId: String
    )
}