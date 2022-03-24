package com.andriawan.sistempenilaianmahasiswa.domain.repository

import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import kotlin.jvm.Throws

interface GradesRepository {
    @Throws(FirebaseFirestoreException::class)
    suspend fun getGrades(
        student_id: String,
        type: String? = null,
        subject: String? = null,
        source: Source = Source.DEFAULT
    ): List<Grades>

    @Throws(FirebaseFirestoreException::class)
    suspend fun createGrades(grades: Grades)

    @Throws(FirebaseFirestoreException::class)
    suspend fun updateGrade(grades: List<Grades>, updateAll: Boolean? = null)

    @Throws(FirebaseFirestoreException::class)
    suspend fun deleteGrade(studentId: String, type: String? = null, subject: String? = null)
}