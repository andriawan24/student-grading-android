package com.andriawan.sistempenilaianmahasiswa.data.repository

import com.andriawan.sistempenilaianmahasiswa.data.mapper.UserDataMapper
import com.andriawan.sistempenilaianmahasiswa.domain.repository.UserRepository
import com.andriawan.sistempenilaianmahasiswa.models.User
import com.andriawan.sistempenilaianmahasiswa.utils.mapper.Mapper
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import kotlin.jvm.Throws

class UserRepositoryImpl(
    private val userDataMapper: UserDataMapper
): UserRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userCollection = db.collection(UserDataMapper.USER_COLLECTION_REF)

    @Throws(FirebaseFirestoreException::class)
    override suspend fun getUser(userId: String?, source: Source): User? {
        if (userId.isNullOrEmpty()) return null
        val currentUserDocRef = userCollection.document(userId)
        val networkUser = currentUserDocRef.get().await().data.orEmpty()
        return userDataMapper.mapIncoming(networkUser)
    }
}