package com.andriawan.sistempenilaianmahasiswa.ui.login

import android.text.Editable
import android.text.TextUtils
import android.util.Patterns
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentLoginBinding
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.utils.DialogHelper
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    override val viewModel: LoginViewModel by viewModels()
    override val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private lateinit var dialogHelper: DialogHelper

    override fun onInitViews() {
        super.onInitViews()
        initDialog()
        initTextEditWatcher()
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.signInResult.observe(this) {
            when (it) {
                Response.Loading -> {
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    dialogHelper.dismiss()
                }

                is Response.Error -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.ERROR,
                            it.error?.originalException?.message.toString()
                        )
                    )
                    Timber.d("Error Login ${it.error?.originalException?.message}")
                }
            }
        }

        viewModel.goToHome.observe(this) {
            it.getContentIfNotHandled()?.let {
                dialogHelper.dismiss()
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginFragmentToDashboardFragment()
                )
            }
        }
    }

    private fun initDialog() {
        dialogHelper = DialogHelper(requireContext(), layoutInflater)
    }

    private fun initTextEditWatcher() {
        binding.emailEditText.addTextChangedListener { text: Editable? ->
            text?.let { emailEditable ->
                val password = binding.passwordEditText.text.toString().trim()
                validateInput(emailEditable.toString(), password)
            }
        }

        binding.passwordEditText.addTextChangedListener { text: Editable? ->
            text?.let { passwordEditable ->
                val email = binding.emailEditText.text.toString().trim()
                validateInput(email, passwordEditable.toString())
            }
        }
    }

    override fun onClickListener() {
        super.onClickListener()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (validateInput(email, password)) {
                viewModel.signIn(email, password)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = false

        if (email.isEmpty()) {
            binding.emailEditTextLayout.isErrorEnabled = true
            binding.emailEditTextLayout.error = getString(R.string.empty_input, "Email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditTextLayout.isErrorEnabled = true
            binding.emailEditTextLayout.error = "Email tidak valid"
        } else {
            binding.emailEditTextLayout.isErrorEnabled = false
            isValid = true
        }

        if (password.isEmpty()) {
            binding.passwordEditTextLayout.isErrorEnabled = true
            binding.passwordEditTextLayout.error = getString(R.string.empty_input, "Password")
        } else {
            binding.passwordEditTextLayout.isErrorEnabled = false
            isValid = true
        }

        return isValid
    }
}