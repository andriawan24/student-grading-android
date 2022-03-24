package com.andriawan.sistempenilaianmahasiswa.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomDeleteGradeBinding
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomDialogFragmentBinding
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomEditGradeBinding
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomHelpDialogFragmentBinding
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogDeleteGradeHelper(private val context: Context, layoutInflater: LayoutInflater) {

    private var binding: CustomDeleteGradeBinding =
        CustomDeleteGradeBinding.inflate(layoutInflater)

    private var alertDialog =
        MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialogRounded)
            .setCancelable(true)
            .setView(binding.root)
            .create()

    private lateinit var dialogState: String

    private fun show(subject: String) {
        binding.titleTextView.visible()
        binding.titleTextView.text = "Hapus nilai $subject?"
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun updateState(
        pair: Pair<String, String>,
        subject: String,
        onSubjectClick: (subject: String) -> Unit
    ) {
        dialogState = pair.first
        when (pair.first) {
            SHOW -> {
                show(subject)
                binding.deleteButton.setOnClickListener {
                    onSubjectClick.invoke(subject)
                }
            }
            HIDE -> alertDialog.hide()
            else -> dismiss()
        }
    }

    fun dismiss() {
        if (alertDialog.isShowing)
            alertDialog.dismiss()
    }

    companion object {
        const val SHOW = "show_dialog"
        const val HIDE = "hide_dialog"
    }
}
