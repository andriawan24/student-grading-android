package com.andriawan.sistempenilaianmahasiswa.data.mapper.grades

import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.utils.mapper.Mapper

/**
 * Mapper for incoming and outgoing data for [Grades] and [Map]
 *
 * @author Naufal Fawwaz Andriawan
 */
class GradeDataMapper: Mapper<Map<String, Any?>, Grades> {

    /**
     * Map data from firebase into local model
     *
     * @param network - Data from firebase, key-value oriented with [Map]
     *
     * @return [Grades] as local data model
     */
    override fun mapIncoming(network: Map<String, Any?>): Grades {
        return Grades(
            grade = network[GRADE_REF] as? Number,
            subject = network[SUBJECT_REF] as? String,
            type = network[TYPE_REF] as? String,
            teacherId = network[TEACHER_ID_REF] as? String,
            studentId = network[STUDENT_ID_REF] as? String,
            id = network[ID_REF] as? String
        )
    }

    /**
     * Map data from local model into firebase data map
     *
     * @param domain - Local data model
     *
     * @return [Map] Mapped local data model into map of [String] and [Any]
     */
    override fun mapOutgoing(domain: Grades): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map[GRADE_REF] = domain.grade
        map[ID_REF] = domain.id
        map[SUBJECT_REF] = domain.subject
        map[TYPE_REF] = domain.type
        map[TEACHER_ID_REF] = domain.teacherId
        map[STUDENT_ID_REF] = domain.studentId

        return map
    }

    /**
     * List fo keys for Firestore table and fields
     */
    companion object {
        const val GRADE_REF = "grade"
        const val ID_REF = "id"
        const val SUBJECT_REF = "subject"
        const val TYPE_REF = "type"
        const val TEACHER_ID_REF = "teacher_id"
        const val STUDENT_ID_REF = "student_id"
    }
}