package com.andriawan.sistempenilaianmahasiswa.utils

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.andriawan.sistempenilaianmahasiswa.R
import com.google.gson.Gson

object Helper {

    fun <T> fetchObjectToString(obj: T): String {
        return Gson().toJson(obj)
    }

    inline fun <reified T> fetchStringToObject(objStr: String): T {
        return Gson().fromJson(objStr, T::class.java)
    }

    fun getKeterangan(textView: TextView, context: Context, gradeText: String): String {
        return when (gradeText.lowercase()) {
            "a" -> {
                textView.setTextColor(Color.parseColor("#019267"))
                context.getString(R.string.nilai_a)
            }

            "a-" -> {
                textView.setTextColor(Color.parseColor("#019267"))
                context.getString(R.string.nilai_amin)
            }

            "b+" -> {
                textView.setTextColor(Color.parseColor("#1572A1"))
                context.getString(R.string.nilai_bplus)
            }

            "b" -> {
                textView.setTextColor(Color.parseColor("#1572A1"))
                context.getString(R.string.nilai_b)
            }

            "b-" -> {
                textView.setTextColor(Color.parseColor("#1572A1"))
                context.getString(R.string.nilai_bmin)
            }

            "c+" -> {
                textView.setTextColor(Color.parseColor("#B33030"))
                context.getString(R.string.nilai_c_plus)
            }


            "c" -> {
                textView.setTextColor(Color.parseColor("#B33030"))
                context.getString(R.string.nilai_c)
            }

            "d" -> {
                textView.setTextColor(ContextCompat.getColor(context, R.color.primary_color))
                context.getString(R.string.nilai_d)
            }

            "e" -> {
                textView.setTextColor(ContextCompat.getColor(context, R.color.primary_color))
                context.getString(R.string.nilai_e)
            }

            else -> {
                "Nilai tidak ditemukan"
            }
        }
    }
}