package com.andriawan.sistempenilaianmahasiswa.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Student(
    var name: String?,
    var nim: String?,
    var phoneNumber: String?,
    var major: String?,
    var entryYear: String?
) : Parcelable