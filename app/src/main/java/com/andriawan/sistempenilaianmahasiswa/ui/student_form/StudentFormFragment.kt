package com.andriawan.sistempenilaianmahasiswa.ui.student_form

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentStudentFormBinding
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.ui.dashboard.DashboardFragment
import com.andriawan.sistempenilaianmahasiswa.utils.DialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentFormFragment : BaseFragment<FragmentStudentFormBinding, StudentFormViewModel>() {
    override val viewModel: StudentFormViewModel by viewModels()
    override val binding: FragmentStudentFormBinding by lazy {
        FragmentStudentFormBinding.inflate(layoutInflater)
    }

    private lateinit var dialogHelper: DialogHelper
    private val args: StudentFormFragmentArgs by navArgs()

    override fun onInitViews() {
        super.onInitViews()
        initToolbar()
        initDialog()
        populateStudent(args.student)
    }

    private fun initToolbar() {
        if (args.isEdit) {
            binding.toolbarTitle.text = getString(R.string.edit_mahasiswa)
        } else {
            binding.toolbarTitle.text = getString(R.string.tambah_mahasiswa)
        }
    }

    private fun populateStudent(student: Student?) {
        student?.let {
            binding.nimEditText.setText(it.nim ?: "")
            binding.nimEditText.isEnabled = false
            binding.nameEditText.setText(it.name ?: "")
            binding.majorEditText.setText(it.major ?: "")
            binding.entryYearEditText.setText(it.entryYear ?: "")
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.studentEditResults.observe(this) {
            when (it) {
                Response.Loading -> {
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.SUCCESS,
                            "Berhasil mengubah mahasiswa"
                        )
                    )
                    dialogHelper.setOnCloseClick {
                        dialogHelper.dismiss()
                        setFragmentResult(
                            DashboardFragment.SUCCESS_UPDATE_STUDENT,
                            bundleOf(Pair("success", true))
                        )
                        findNavController().navigateUp()
                    }
                }

                is Response.Error -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.ERROR,
                            it.error?.originalException?.message ?: ""
                        )
                    )

                    dialogHelper.setOnCloseClick {
                        dialogHelper.dismiss()
                    }
                }
            }
        }

        viewModel.createStudentResponse.observe(this) {
            when (it) {
                Response.Loading -> {
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.SUCCESS,
                            "Berhasil menambahkan mahasiswa"
                        )
                    )
                    dialogHelper.setOnCloseClick {
                        dialogHelper.dismiss()
                        setFragmentResult(
                            DashboardFragment.SUCCESS_CREATE_STUDENT,
                            bundleOf(Pair("success", true))
                        )
                        findNavController().navigateUp()
                    }
                }

                is Response.Error -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.ERROR,
                            it.error?.originalException?.message ?: ""
                        )
                    )

                    dialogHelper.setOnCloseClick {
                        dialogHelper.dismiss()
                    }
                }
            }
        }
    }

    private fun initDialog() {
        dialogHelper = DialogHelper(requireContext(), layoutInflater)
    }

    override fun onClickListener() {
        super.onClickListener()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val nim = binding.nimEditText.text.toString().trim()
            val entryYear = binding.entryYearEditText.text.toString().trim()
            val major = binding.majorEditText.text.toString().trim()

            if (validateInput(name, nim, entryYear, major)) {
                viewModel.saveStudent(name, nim, entryYear, major, args.isEdit)
            }
        }
    }

    private fun validateInput(
        name: String,
        nim: String,
        entryYear: String,
        major: String,
    ): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.nameEditTextLayout.isErrorEnabled = true
            binding.nameEditTextLayout.error = getString(R.string.empty_input, "Nama")
            isValid = false
        } else {
            binding.nameEditTextLayout.isErrorEnabled = false
        }

        if (nim.isEmpty()) {
            binding.nimEditTextLayout.isErrorEnabled = true
            binding.nimEditTextLayout.error = getString(R.string.empty_input, "NIM")
            isValid = false
        } else {
            binding.nimEditTextLayout.isErrorEnabled = false
        }

        if (major.isEmpty()) {
            binding.majorEditTextLayout.isErrorEnabled = true
            binding.majorEditTextLayout.error = getString(R.string.empty_input, "Jurusan")
            isValid = false
        } else {
            binding.majorEditTextLayout.isErrorEnabled = false
        }

        if (entryYear.isEmpty()) {
            binding.entryYearEditTextLayout.isErrorEnabled = true
            binding.entryYearEditTextLayout.error = getString(R.string.empty_input, "Tahun masuk")
            isValid = false
        } else {
            binding.entryYearEditTextLayout.isErrorEnabled = false
        }

        return isValid
    }
}