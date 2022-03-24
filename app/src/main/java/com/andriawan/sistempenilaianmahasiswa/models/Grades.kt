package com.andriawan.sistempenilaianmahasiswa.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Grades(
    var id: String?,
    var grade: Number?,
    val subject: String?,
    val type: String?,
    val teacherId: String?,
    val studentId: String?,
): Parcelable
