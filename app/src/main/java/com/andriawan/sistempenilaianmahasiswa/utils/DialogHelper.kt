package com.andriawan.sistempenilaianmahasiswa.utils

import android.content.Context
import android.view.LayoutInflater
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.databinding.CustomDialogFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogHelper(private val context: Context, layoutInflater: LayoutInflater) {

    private var binding: CustomDialogFragmentBinding =
        CustomDialogFragmentBinding.inflate(layoutInflater)

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

    private fun showLoading() {
        binding.loadingProgressBar.visible()
        binding.titleTextView.text = context.getString(R.string.loading_text)
        binding.titleTextView.visible()
        binding.descriptionTextView.hide()
        binding.closeDialogButton.hide()
        alertDialog.show()
    }

    private fun showSuccess(message: String) {
        binding.loadingProgressBar.hide()
        binding.titleTextView.text = "Success"
        binding.titleTextView.visible()
        binding.descriptionTextView.visible()
        binding.descriptionTextView.text = message
        binding.closeDialogButton.visible()
        alertDialog.show()
    }

    private fun showError(message: String) {
        binding.loadingProgressBar.hide()
        binding.titleTextView.text = "Error"
        binding.titleTextView.visible()
        binding.descriptionTextView.visible()
        binding.descriptionTextView.text = message
        binding.closeDialogButton.visible()
        alertDialog.show()
    }

    fun updateState(pair: Pair<String, String>) {
        dialogState = pair.first
        when (pair.first) {
            LOADING -> showLoading()
            SUCCESS -> showSuccess(pair.second)
            ERROR -> showError(pair.second)
            HIDE -> alertDialog.hide()
            else -> dismiss()
        }
    }

    fun setOnCloseClick(onCloseClick : () -> Unit) {
        onConfirmClick = onCloseClick
    }

    fun setButtonTitle(title: String) {
        binding.closeDialogButton.text = title
    }

    fun dismiss() {
        if (alertDialog.isShowing)
            alertDialog.dismiss()
    }

    companion object {
        const val LOADING = "loading_dialog"
        const val SUCCESS = "success_dialog"
        const val ERROR = "error_dialog"
        const val HIDE = "hide_dialog"
    }
}
