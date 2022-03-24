package com.andriawan.sistempenilaianmahasiswa.utils

import android.content.Context
import android.view.LayoutInflater
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomEditOrDeleteStudentFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogEditDeleteStudentHelper(context: Context, layoutInflater: LayoutInflater) {

    private var binding: CustomEditOrDeleteStudentFragmentBinding =
        CustomEditOrDeleteStudentFragmentBinding.inflate(layoutInflater)

    private var alertDialog =
        MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialogRounded)
            .setCancelable(true)
            .setView(binding.root)
            .create()

    lateinit var onConfirmClick: () -> Unit
    lateinit var dialogState: String

    init {
        binding.closeDialogButton.setOnClickListener {
            if (this::onConfirmClick.isInitialized) {
                onConfirmClick.invoke()
            } else {
                dismiss()
            }
        }
    }

    private fun show(message: String) {
        binding.titleTextView.visible()
        binding.descriptionTextView.hide()
        binding.descriptionTextView.text = message
        binding.closeDialogButton.visible()
        alertDialog.show()
    }

    fun <DATA> updateState(
        pair: Pair<String, String>,
        data: DATA,
        onDeleteClicked: (data: DATA) -> Unit,
        onEditClicked: (data: DATA) -> Unit
    ) {
        dialogState = pair.first
        when (pair.first) {
            SHOW -> {
                binding.closeDialogButton.setOnClickListener {
                    onDeleteClicked.invoke(data)
                }

                binding.editButton.setOnClickListener {
                    onEditClicked.invoke(data)
                }

                show(pair.second)
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
