package com.andriawan.sistempenilaianmahasiswa.data.mapper

import com.andriawan.sistempenilaianmahasiswa.models.User
import com.andriawan.sistempenilaianmahasiswa.utils.mapper.Mapper

class UserDataMapper: Mapper<Map<String, Any>, User?> {

    override fun mapOutgoing(domain: User?): Map<String, Any> {
        val result = mutableMapOf<String, Any>()
        domain?.let {
            result[USER_ID_REF] = it.userId
            result[USER_NAME_REF] = it.name
            result[USER_EMAIL_REF] = it.email
        }

        return result
    }

    override fun mapIncoming(network: Map<String, Any>): User? {
        val id = network[USER_ID_REF] as? String ?: return null
        val name = network[USER_NAME_REF] as? String ?: return null
        val email = network[USER_EMAIL_REF] as? String ?: return null

        return User(id, name, email)
    }

    companion object {
        const val USER_COLLECTION_REF = "users"
        const val USER_ID_REF = "user_id"
        const val USER_NAME_REF = "name"
        const val USER_EMAIL_REF = "email"
    }
}