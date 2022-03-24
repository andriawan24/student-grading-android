package com.andriawan.sistempenilaianmahasiswa.models

import android.os.Parcelable
import com.google.firebase.auth.FirebaseAuth
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: "",
    var name: String,
    var email: String,
): Parcelable