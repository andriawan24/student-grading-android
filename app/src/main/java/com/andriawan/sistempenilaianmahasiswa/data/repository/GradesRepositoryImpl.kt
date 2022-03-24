package com.andriawan.sistempenilaianmahasiswa.data.repository

import com.andriawan.sistempenilaianmahasiswa.data.mapper.grades.GradeDataMapper
import com.andriawan.sistempenilaianmahasiswa.data.mapper.grades.GradesDataMapper
import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.jvm.Throws

class GradesRepositoryImpl(
    private val gradesDataMapper: GradesDataMapper,
    private val gradeDataMapper: GradeDataMapper
) : GradesRepository {

    private val db = FirebaseFirestore.getInstance()
    private val gradesCollection = db.collection(GradesDataMapper.GRADES_COLLECTION_REF)

    @Throws(FirebaseFirestoreException::class)
    override suspend fun getGrades(
        student_id: String,
        type: String?,
        subject: String?,
        source: Source
    ): List<Grades> {
        var gradeQuery = gradesCollection.whereEqualTo("student_id", student_id)

        if (type != null) {
            gradeQuery = gradeQuery.whereEqualTo("type", type)
        }

        if (subject != null) {
            gradeQuery = gradeQuery.whereEqualTo("subject", subject)
        }

        val gradesList = gradeQuery.get()
            .await()
            .documents

        val gradesMapList = mutableListOf<Map<String, Any?>>()
        gradesList.forEach {
            it.data?.let { student ->
                gradesMapList.add(student)
            }
        }

        return gradesDataMapper.mapIncoming(gradesMapList)
    }

    @Throws(FirebaseFirestoreException::class)
    override suspend fun createGrades(grades: Grades) {
        val gradeDocRef = gradesCollection.whereEqualTo("subject", grades.subject)
            .whereEqualTo("type", grades.type)
        val networkGrades = gradeDocRef.get().await().documents
        if (networkGrades.size > 0) {
            throw FirebaseFirestoreException(
                "Mata Kuliah Sudah Dinilai",
                FirebaseFirestoreException.Code.UNAVAILABLE
            )
        }

        db.runTransaction { transaction ->
            val currentDocRef = gradesCollection.document()
            val gradeDoc = transaction.get(currentDocRef)

            if (!gradeDoc.exists()) {
                grades.id = gradeDoc.id
                transaction.set(currentDocRef, gradeDataMapper.mapOutgoing(grades))
            }
        }.await()
    }

    override suspend fun updateGrade(grades: List<Grades>, updateAll: Boolean?) {
        grades.forEach { grade ->
            var gradeDocRef = gradesCollection.whereEqualTo("student_id", grade.studentId)

            gradeDocRef =
                if (updateAll == true) {
                    gradeDocRef.whereEqualTo("subject", grade.subject)
                        .whereEqualTo("type", grade.type)
                } else {
                    gradeDocRef.whereEqualTo("id", grade.id)
                }

            val networkGrades = gradeDocRef.get().await().documents
            if (networkGrades.isEmpty()) {
                throw FirebaseFirestoreException(
                    "Nilai tidak ditemukan",
                    FirebaseFirestoreException.Code.NOT_FOUND
                )
            }

            Timber.d("Network Grades $networkGrades, grade $grade")

            db.runTransaction { transaction ->
                transaction.set(networkGrades[0].reference, gradeDataMapper.mapOutgoing(grade))
            }.await()
        }
    }

    override suspend fun deleteGrade(studentId: String, type: String?, subject: String?) {
        var gradeQuery = gradesCollection.whereEqualTo("student_id", studentId)

        if (type != null) {
            gradeQuery = gradeQuery.whereEqualTo("type", type)
        }

        if (subject != null) {
            gradeQuery = gradeQuery.whereEqualTo("subject", subject)
        }

        val gradesList = gradeQuery.get()
            .await()
            .documents

        db.runTransaction {
            gradesList.forEach { doc ->
                it.delete(doc.reference)
            }
        }.await()
    }
}