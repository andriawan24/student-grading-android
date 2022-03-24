package com.andriawan.sistempenilaianmahasiswa.models.response

import androidx.annotation.Keep
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.FirebaseFirestoreException.Code.*

@Keep
class ErrorHandlerImpl: ErrorHandler {
    override fun getError(firebaseException: FirebaseFirestoreException): ErrorEntity {
        return when(firebaseException.code) {
            CANCELLED -> ErrorEntity.Network(firebaseException)
            ABORTED -> ErrorEntity.Network(firebaseException)
            NOT_FOUND -> ErrorEntity.NotFound(firebaseException)
            PERMISSION_DENIED -> ErrorEntity.AccessDenied(firebaseException)
            UNAVAILABLE -> ErrorEntity.ServiceUnavailable(firebaseException)
            UNAUTHENTICATED -> ErrorEntity.AccessDenied(firebaseException)
            else -> ErrorEntity.Unknown(firebaseException)
        }
    }
}