package com.andriawan.sistempenilaianmahasiswa.data.mapper.grades

import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.utils.mapper.ListMapper

/**
 * Mapper for incoming and outgoing list data for [Grades] and [Map]
 *
 * @author Naufal Fawwaz Andriawan
 */
class GradesDataMapper : ListMapper<Map<String, Any?>, Grades> {

    /**
     * Map data from firebase into local model
     *
     * @param network - Data list from firebase, each of list is the key-value data oriented with [Map]
     *
     * @return [List] of [Grades] as a list of local data model
     */
    override fun mapIncoming(network: List<Map<String, Any?>>): List<Grades> {
        val gradeList = mutableListOf<Grades>()
        network.map {
            gradeList.add(
                Grades(
                    grade = it[GRADE_REF] as? Number,
                    subject = it[SUBJECT_REF] as? String,
                    type = it[TYPE_REF] as? String,
                    teacherId = it[TEACHER_ID_REF] as? String,
                    studentId = it[STUDENT_ID_REF] as? String,
                    id = it[ID_REF] as? String
                )
            )
        }

        return gradeList
    }

    /**
     * Map data from local list model into firebase data map
     *
     * @param domain - Local list data model
     *
     * @return [Map] Mapped local list data model into [List] of map [String] and [Any]
     */
    override fun mapOutgoing(domain: List<Grades>): List<Map<String, Any?>> {
        val result = mutableListOf<Map<String, Any?>>()

        domain.forEach { data ->
            val map = mutableMapOf<String, Any?>()
            map[GRADE_REF] = data.grade
            map[SUBJECT_REF] = data.subject
            map[TYPE_REF] = data.type
            map[TEACHER_ID_REF] = data.teacherId
            map[STUDENT_ID_REF] = data.studentId
            map[ID_REF] = data.id

            result.add(map)
        }

        return result
    }

    /**
     * List fo keys for Firestore table and fields
     */
    companion object {
        const val GRADES_COLLECTION_REF = "grades"
        const val GRADE_REF = "grade"
        const val ID_REF = "id"
        const val SUBJECT_REF = "subject"
        const val TYPE_REF = "type"
        const val TEACHER_ID_REF = "teacher_id"
        const val STUDENT_ID_REF = "student_id"
    }
}