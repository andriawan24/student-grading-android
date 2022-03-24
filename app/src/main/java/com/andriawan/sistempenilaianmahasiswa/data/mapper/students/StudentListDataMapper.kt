package com.andriawan.sistempenilaianmahasiswa.data.mapper.students

import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.utils.mapper.ListMapper

/**
 * Mapper for list of student data for Firebase and Local
 *
 * @author Naufal Fawwaz Andriawan
 */
class StudentListDataMapper: ListMapper<Map<String, Any?>, Student> {

    /**
     * Map incoming data from firebase
     *
     * @param network - [List] for [Map] of [String] and [Any]
     *
     * @return [List] of [Student]
     */
    override fun mapIncoming(network: List<Map<String, Any?>>): List<Student> {
        val studentLists = mutableListOf<Student>()
        network.map {
            studentLists.add(
                Student(
                    name = it[NAME_COLLECTION_REF] as? String,
                    nim = it[NIM_COLLECTION_REF] as? String,
                    phoneNumber = it[PHONE_COLLECTION_REF] as? String,
                    major = it[MAJOR_COLLECTION_REF] as? String,
                    entryYear = it[ENTRY_YEAR_COLLECTION_REF] as? String
                )
            )
        }

        return studentLists
    }

    /**
     * Map outgoing data for firebase
     *
     * @param domain - [List] of [Student]
     *
     * @return [List] for [Map] of [String] and [Any]
     */
    override fun mapOutgoing(domain: List<Student>): List<Map<String, Any?>> {
        val result = mutableListOf<Map<String, Any?>>()

        domain.forEach { data ->
            val map = mutableMapOf<String, Any?>()
            map[NAME_COLLECTION_REF] = data.name
            map[NIM_COLLECTION_REF] = data.nim
            map[MAJOR_COLLECTION_REF] = data.major
            map[ENTRY_YEAR_COLLECTION_REF] = data.entryYear
            map[PHONE_COLLECTION_REF] = data.phoneNumber

            result.add(map)
        }

        return result
    }

    companion object {
        const val STUDENT_COLLECTION_REF = "students"
        const val NAME_COLLECTION_REF = "name"
        const val NIM_COLLECTION_REF = "nim"
        const val MAJOR_COLLECTION_REF = "major"
        const val ENTRY_YEAR_COLLECTION_REF = "entry_year"
        const val PHONE_COLLECTION_REF = "phone_number"
    }
}
