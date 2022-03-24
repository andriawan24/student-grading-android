package com.andriawan.sistempenilaianmahasiswa.data.repository

import com.andriawan.sistempenilaianmahasiswa.data.mapper.students.StudentDataMapper
import com.andriawan.sistempenilaianmahasiswa.data.mapper.students.StudentListDataMapper
import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.jvm.Throws

class StudentsRepositoryImpl(
    private val studentListDataMapper: StudentListDataMapper,
    private val studentDataMapper: StudentDataMapper
) : StudentsRepository {

    private val db = FirebaseFirestore.getInstance()
    private val studentsCollection = db.collection(StudentListDataMapper.STUDENT_COLLECTION_REF)

    @Throws(FirebaseFirestoreException::class)
    override suspend fun getStudents(query: String?, source: Source): List<Student> {
        val studentMapList = mutableListOf<Map<String, Any?>>()
        val studentCollections = studentsCollection.get().await().documents

        studentCollections.forEach {
            it.data?.let { student ->
                studentMapList.add(student)
            }
        }

        Timber.d("Network Students $studentMapList with query $query")
        return studentListDataMapper.mapIncoming(studentMapList)
    }

    override suspend fun getStudent(studentId: String, source: Source): Student? {
        val studentDocRef = studentsCollection.whereEqualTo("nim", studentId)
        val networkStudent = studentDocRef.get().await().documents
        Timber.d("Student with id $studentId, have $networkStudent ${networkStudent.size}")
        return if (networkStudent.size > 0) {
            studentDataMapper.mapIncoming(networkStudent[0].data!!)
        } else {
            null
        }
    }

    override suspend fun createStudent(student: Student) {
        val studentDocRef = studentsCollection.whereEqualTo("nim", student.nim)
        val networkStudent = studentDocRef.get().await().documents
        Timber.d("Student with id $student, have $networkStudent ${networkStudent.size}")
        if (networkStudent.size > 0) {
            throw FirebaseFirestoreException(
                "NIM Sudah dipakai",
                FirebaseFirestoreException.Code.UNAVAILABLE
            )
        }

        db.runTransaction { transaction ->
            val currentDocRef = studentsCollection.document()
            val studentDoc = transaction.get(currentDocRef)

            if (!studentDoc.exists()) {
                transaction.set(currentDocRef, studentDataMapper.mapOutgoing(student))
            }
        }.await()
    }

    override suspend fun editStudent(student: Student) {
        val studentDocRef = studentsCollection.whereEqualTo("nim", student.nim)
        val networkStudent = studentDocRef.get().await().documents
        Timber.d("Student with id $student, have $networkStudent ${networkStudent.size}")
        if (networkStudent.size > 1) {
            throw FirebaseFirestoreException(
                "NIM Sudah dipakai",
                FirebaseFirestoreException.Code.UNAVAILABLE
            )
        }
        db.runTransaction {
            it.set(networkStudent[0].reference, studentDataMapper.mapOutgoing(student))
        }.await()
    }

    override suspend fun deleteStudent(studentId: String) {
        val studentDocRef = studentsCollection.whereEqualTo("nim", studentId)
        val networkStudent = studentDocRef.get().await().documents
        db.runTransaction {
            it.delete(networkStudent[0].reference)
        }.await()
    }
}