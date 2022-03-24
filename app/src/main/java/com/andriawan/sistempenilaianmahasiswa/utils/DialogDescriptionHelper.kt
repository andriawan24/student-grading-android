package com.andriawan.sistempenilaianmahasiswa.utils

import android.content.Context
import android.view.LayoutInflater
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomDialogFragmentBinding
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomHelpDialogFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogDescriptionHelper(context: Context, layoutInflater: LayoutInflater) {

    private var binding: CustomHelpDialogFragmentBinding =
        CustomHelpDialogFragmentBinding.inflate(layoutInflater)

    private var alertDialog = MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialogRounded)
        .setCancelable(false)
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
        binding.descriptionTextView.visible()
        binding.descriptionTextView.text = message
        binding.closeDialogButton.visible()
        alertDialog.show()
    }

    fun updateState(pair: Pair<String, String>) {
        dialogState = pair.first
        when (pair.first) {
            SHOW -> show(pair.second)
            HIDE -> alertDialog.hide()
            else -> dismiss()
        }
    }

    private fun dismiss() {
        if (alertDialog.isShowing)
            alertDialog.dismiss()
    }

    companion object {
        const val SHOW = "show_dialog"
        const val HIDE = "hide_dialog"
    }
}
