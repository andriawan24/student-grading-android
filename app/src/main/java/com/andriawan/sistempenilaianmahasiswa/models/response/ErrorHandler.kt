package com.andriawan.sistempenilaianmahasiswa.models.response

import com.google.firebase.firestore.FirebaseFirestoreException

interface ErrorHandler {
    fun getError(firebaseException: FirebaseFirestoreException): ErrorEntity
}