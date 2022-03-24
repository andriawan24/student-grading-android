package com.andriawan.sistempenilaianmahasiswa.data.mapper.students

import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.utils.mapper.Mapper

/**
 * Mapper for student data for Firebase and Local
 *
 * @author Naufal Fawwaz Andriawan
 */
class StudentDataMapper: Mapper<Map<String, Any?>, Student> {

    /**
     * Map incoming data from network/firebase into [Student]
     *
     * @param network - [Map] of [String] and [Any]
     *
     * @return [Student]
     */
    override fun mapIncoming(network: Map<String, Any?>): Student {
        return Student(
            name = network[StudentListDataMapper.NAME_COLLECTION_REF] as? String,
            nim = network[StudentListDataMapper.NIM_COLLECTION_REF] as? String,
            phoneNumber = network[StudentListDataMapper.PHONE_COLLECTION_REF] as? String,
            major = network[StudentListDataMapper.MAJOR_COLLECTION_REF] as? String,
            entryYear = network[StudentListDataMapper.ENTRY_YEAR_COLLECTION_REF] as? String
        )
    }

    /**
     * Map outgoing data from [Student] into network/firebase
     *
     * @param domain - [Student] model
     *
     * @return [Map] of [String] and [Any]
     */
    override fun mapOutgoing(domain: Student): Map<String, Any?> {
        val student = mutableMapOf<String, Any?>()
        student[StudentListDataMapper.NAME_COLLECTION_REF] = domain.name
        student[StudentListDataMapper.NIM_COLLECTION_REF] = domain.nim
        student[StudentListDataMapper.MAJOR_COLLECTION_REF] = domain.major
        student[StudentListDataMapper.ENTRY_YEAR_COLLECTION_REF] = domain.entryYear
        student[StudentListDataMapper.PHONE_COLLECTION_REF] = domain.phoneNumber

        return student
    }
}