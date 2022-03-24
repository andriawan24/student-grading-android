package com.andriawan.sistempenilaianmahasiswa.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomDialogFragmentBinding
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomEditGradeBinding
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomHelpDialogFragmentBinding
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogEditGradeHelper(private val context: Context, layoutInflater: LayoutInflater) {

    private var binding: CustomEditGradeBinding =
        CustomEditGradeBinding.inflate(layoutInflater)

    private var alertDialog =
        MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialogRounded)
            .setCancelable(false)
            .setView(binding.root)
            .create()

    private lateinit var dialogState: String

    private fun show(grade: String) {
        binding.titleTextView.visible()
        binding.gradeEditText.setText(grade)
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun updateState(
        pair: Pair<String, String>,
        grade: Grades,
        onGradeClick: (grade: Grades) -> Unit
    ) {
        dialogState = pair.first
        when (pair.first) {
            SHOW -> {
                show(pair.second)
                binding.titleTextView.text = "Ubah nilai ${grade.subject}"
                binding.gradeEditText.setText(grade.grade.toString())
                binding.editButton.setOnClickListener {
                    val gradeInput = binding.gradeEditText.text.toString()
                    when {
                        gradeInput.isEmpty() -> {
                            binding.gradeEditTextLayout.isErrorEnabled = true
                            binding.gradeEditTextLayout.error =
                                context.getString(R.string.empty_input, "Nilai")
                            return@setOnClickListener
                        }
                        gradeInput.toInt() !in 0..100 -> {
                            binding.gradeEditTextLayout.isErrorEnabled = true
                            binding.gradeEditTextLayout.error =
                                context.getString(R.string.more_than_hundred_input, "Nilai")
                            return@setOnClickListener
                        }
                        else -> {
                            binding.gradeEditTextLayout.isErrorEnabled = false
                            grade.grade = gradeInput.toInt()
                            onGradeClick.invoke(grade)
                        }
                    }
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
