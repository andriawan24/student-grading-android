package com.andriawan.sistempenilaianmahasiswa.ui.grade_form

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentGradeFormBinding
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.ui.detail.DetailFragment
import com.andriawan.sistempenilaianmahasiswa.utils.DialogHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GradeFormFragment : BaseFragment<FragmentGradeFormBinding, GradeFormViewModel>() {
    override val viewModel: GradeFormViewModel by viewModels()
    override val binding: FragmentGradeFormBinding by lazy {
        FragmentGradeFormBinding.inflate(layoutInflater)
    }

    private val args: GradeFormFragmentArgs by navArgs()
    private lateinit var dialogHelper: DialogHelper

    override fun onInitViews() {
        super.onInitViews()
        populateStudent()
        initDialog()

        if (args.isUpdate) {
            args.student.nim?.let { nim ->
                viewModel.getGrade(nim, args.subject)
            }
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.getGradeResponse.observe(this) {
            when (it) {
                Response.Loading -> {
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    it.data?.let { data ->
                        data.forEach { grade ->
                            binding.subjectEditText.setText(grade.subject)
                            binding.subjectEditTextLayout.isEnabled = false
                            binding.subjectEditTextLayout.boxStrokeColor =
                                Color.parseColor("#4DADADAD")
                            binding.subjectEditTextLayout.boxBackgroundColor =
                                Color.parseColor("#C3C3C3")

                            when (grade.type!!) {
                                DetailFragment.TYPE_TASKS -> {
                                    binding.tasksEditText.setText(grade.grade.toString())
                                }

                                DetailFragment.TYPE_QUIZ -> {
                                    binding.quizEditText.setText(grade.grade.toString())
                                }

                                DetailFragment.TYPE_UTS -> {
                                    binding.utsEditText.setText(grade.grade.toString())
                                }

                                DetailFragment.TYPE_UAS -> {
                                    binding.uasEditText.setText(grade.grade.toString())
                                }
                            }
                        }
                    }

                    dialogHelper.dismiss()
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

        viewModel.createGradesResponse.observe(this) {
            when (it) {
                Response.Loading -> {
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.SUCCESS,
                            if (args.isUpdate) {
                                "Berhasil mengubah nilai"
                            } else {
                                "Berhasil menambahkan nilai"
                            }
                        )
                    )
                    dialogHelper.setOnCloseClick {
                        setFragmentResult(
                            DetailFragment.SUCCESS_CREATE_GRADE,
                            bundleOf(Pair("success", true))
                        )
                        dialogHelper.dismiss()
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

    @SuppressLint("SetTextI18n")
    private fun populateStudent() {
        args.student.let { student ->
            binding.nameEditText.setText("${student.name} \u2022 ${student.nim}")
        }
    }

    override fun onClickListener() {
        super.onClickListener()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.saveButton.setOnClickListener {
            val subject = binding.subjectEditText.text.toString().trim()
            val tasks = binding.tasksEditText.text.toString().trim()
            val quiz = binding.quizEditText.text.toString().trim()
            val uts = binding.utsEditText.text.toString().trim()
            val uas = binding.uasEditText.text.toString().trim()

            if (validateInput(subject, tasks, quiz, uts, uas)) {
                if (args.isUpdate) {
                    viewModel.editGrades(args.student.nim ?: "", subject, tasks, quiz, uts, uas)
                } else {
                    viewModel.saveGrades(args.student.nim ?: "", subject, tasks, quiz, uts, uas)
                }
            }
        }
    }

    private fun validateInput(
        subject: String,
        tasksGrade: String,
        quizGrade: String,
        utsGrade: String,
        uasGrade: String
    ): Boolean {
        var isValid = true

        if (subject.isEmpty()) {
            binding.subjectEditTextLayout.isErrorEnabled = true
            binding.subjectEditTextLayout.error = getString(R.string.empty_input, "Mata kuliah")
            isValid = false
        } else {
            binding.subjectEditTextLayout.isErrorEnabled = false
        }

        when {
            tasksGrade.isEmpty() -> {
                binding.tasksEditTextLayout.isErrorEnabled = true
                binding.tasksEditTextLayout.error = getString(R.string.empty_input, "Nilai tugas")
                isValid = false
            }
            tasksGrade.toInt() !in 0..100 -> {
                binding.tasksEditTextLayout.isErrorEnabled = true
                binding.tasksEditTextLayout.error =
                    getString(R.string.more_than_hundred_input, "Nilai tugas")
                isValid = false
            }
            else -> {
                binding.tasksEditTextLayout.isErrorEnabled = false
            }
        }

        when {
            quizGrade.isEmpty() -> {
                binding.quizEditTextLayout.isErrorEnabled = true
                binding.quizEditTextLayout.error = getString(R.string.empty_input, "Nilai quiz")
                isValid = false
            }
            quizGrade.toInt() !in 0..100 -> {
                binding.quizEditTextLayout.isErrorEnabled = true
                binding.quizEditTextLayout.error =
                    getString(R.string.more_than_hundred_input, "Nilai quiz")
                isValid = false
            }
            else -> {
                binding.quizEditTextLayout.isErrorEnabled = false
            }
        }

        when {
            utsGrade.isEmpty() -> {
                binding.utsEditTextLayout.isErrorEnabled = true
                binding.utsEditTextLayout.error = getString(R.string.empty_input, "Nilai UTS")
                isValid = false
            }
            utsGrade.toInt() !in 0..100 -> {
                binding.utsEditTextLayout.isErrorEnabled = true
                binding.utsEditTextLayout.error =
                    getString(R.string.more_than_hundred_input, "Nilai UTS")
                isValid = false
            }
            else -> {
                binding.utsEditTextLayout.isErrorEnabled = false
            }
        }

        when {
            uasGrade.isEmpty() -> {
                binding.uasEditTextLayout.isErrorEnabled = true
                binding.uasEditTextLayout.error = getString(R.string.empty_input, "Nilai UAS")
                isValid = false
            }
            uasGrade.toInt() !in 0..100 -> {
                binding.uasEditTextLayout.isErrorEnabled = true
                binding.uasEditTextLayout.error =
                    getString(R.string.more_than_hundred_input, "Nilai UAS")
                isValid = false
            }
            else -> {
                binding.uasEditTextLayout.isErrorEnabled = false
            }
        }

        return isValid
    }
}