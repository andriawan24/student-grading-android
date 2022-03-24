package com.andriawan.sistempenilaianmahasiswa.domain.repository

import com.andriawan.sistempenilaianmahasiswa.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import kotlin.jvm.Throws

interface UserRepository {

    @Throws(FirebaseFirestoreException::class)
    suspend fun getUser(
        userId: String? = FirebaseAuth.getInstance().currentUser?.uid,
        source: Source = Source.DEFAULT
    ): User?
}