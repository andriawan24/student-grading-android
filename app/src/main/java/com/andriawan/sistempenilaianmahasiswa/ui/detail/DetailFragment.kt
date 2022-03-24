package com.andriawan.sistempenilaianmahasiswa.ui.detail

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.adapters.GradeDetailAdapter
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentDetailBinding
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {
    override val viewModel: DetailViewModel by viewModels()
    override val binding: FragmentDetailBinding by lazy {
        FragmentDetailBinding.inflate(layoutInflater)
    }

    private val args: DetailFragmentArgs by navArgs()
    private lateinit var dialogHelper: DialogHelper
    private lateinit var dialogDescriptionHelper: DialogDescriptionHelper
    private lateinit var dialogDeleteGradeHelper: DialogDeleteGradeHelper
    private lateinit var adapter: GradeDetailAdapter

    override fun onInitViews() {
        super.onInitViews()
        initDialog()
        initRecycler()

        viewModel.getStudent(args.studentId)
        viewModel.getGrades(args.studentId)

        setFragmentResultListener(SUCCESS_CREATE_GRADE) { _, bundle ->
            if (bundle.getBoolean("success")) {
                viewModel.getGrades(args.studentId)
            }
        }
    }

    private fun initRecycler() {
        adapter = GradeDetailAdapter({ subject ->
            dialogDeleteGradeHelper.updateState(Pair(DialogDeleteGradeHelper.SHOW, ""), subject) {
                viewModel.deleteGrades(args.studentId, it)
                dialogDeleteGradeHelper.dismiss()
            }
        }, {
            viewModel.student.value?.let { student ->
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToGradeFormFragment(
                        student,
                        isUpdate = true,
                        subject = it
                    )
                )
            } ?: kotlin.run {
                Snackbar.make(binding.root, "Gagal mengubah nilai", Snackbar.LENGTH_SHORT).show()
            }
        })
        binding.gradesDetailRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.gradesDetailRecyclerView.adapter = adapter
    }

    private fun initDialog() {
        dialogHelper = DialogHelper(requireContext(), layoutInflater)
        dialogDescriptionHelper = DialogDescriptionHelper(requireContext(), layoutInflater)
        dialogDeleteGradeHelper = DialogDeleteGradeHelper(requireContext(), layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.gradeDetail.observe(this) {
            adapter.setData(it)
        }

        viewModel.studentGrades.observe(this) {
            if (it.second == 0.0) {
                binding.gradeAvaliableLayout.hide()
                binding.gradeEmptyTextView.visible()
            } else {
                binding.gradeAvaliableLayout.visible()
                binding.scoreTextTextView.text = it.first
                binding.totalScoreTextView.text =
                    "Total nilai : ${String.format("%.2f", it.second)}"
                binding.keteranganTextTextView.text =
                    Helper.getKeterangan(
                        binding.scoreTextTextView,
                        requireContext(),
                        it.first
                    )
                binding.gradeEmptyTextView.hide()
            }
        }

        viewModel.gradeDeleteResponse.observe(this) {
            when (it) {
                Response.Loading -> {
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    dialogHelper.dismiss()
                    Snackbar.make(
                        binding.root,
                        "Berhasil menghapus nilai",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("OK") {}.show()

                    viewModel.getGrades(args.studentId)
                }

                is Response.Error -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.ERROR,
                            it.error?.originalException?.message.toString()
                        )
                    )
                }
            }
        }

        viewModel.studentResponse.observe(this) {
            when (it) {
                Response.Loading -> {
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    it.data?.let { student ->
                        binding.nameTextView.text = student.name
                        binding.majorTextView.text = getString(
                            R.string.major_entry_year,
                            student.major ?: "",
                            student.entryYear ?: ""
                        )
                        binding.nimTextView.text = "NIM ${student.nim}"
                    }

                    dialogHelper.dismiss()
                }

                is Response.Error -> {
                    dialogHelper.updateState(
                        Pair(
                            DialogHelper.ERROR,
                            it.error?.originalException?.message.toString()
                        )
                    )
                }
            }
        }
    }

    override fun onClickListener() {
        super.onClickListener()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.menu_help) {
                dialogDescriptionHelper.updateState(
                    Pair(
                        DialogDescriptionHelper.SHOW,
                        getString(R.string.help_text)
                    )
                )

                return@setOnMenuItemClickListener true
            }

            false
        }

        binding.totalGradeMaterialCardView.setOnClickListener {
            if (binding.gradesDetailRecyclerView.isVisible) {
                binding.detailScoreTextView.text = getString(R.string.lihat_detail)
                binding.gradesDetailRecyclerView.hide()
            } else {
                binding.detailScoreTextView.text = getString(R.string.lihat_lebih_sedikit)
                binding.gradesDetailRecyclerView.visible()
            }
        }

        binding.tasksMaterialCardView.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToGradeDetailFragment(
                    studentId = args.studentId,
                    gradeType = TYPE_TASKS
                )
            )
        }

        binding.quizGradeMaterialCardView.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToGradeDetailFragment(
                    studentId = args.studentId,
                    gradeType = TYPE_QUIZ
                )
            )
        }

        binding.utsGradeMaterialCardView.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToGradeDetailFragment(
                    studentId = args.studentId,
                    gradeType = TYPE_UTS
                )
            )
        }

        binding.uasGradeMaterialCardView.setOnClickListener {
            findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToGradeDetailFragment(
                    studentId = args.studentId,
                    gradeType = TYPE_UAS
                )
            )
        }

        binding.addGradeButton.setOnClickListener {
            viewModel.student.value?.let { student ->
                findNavController().navigate(
                    DetailFragmentDirections.actionDetailFragmentToGradeFormFragment(
                        student,
                        isUpdate = false,
                        subject = null
                    )
                )
            } ?: kotlin.run {
                Snackbar.make(binding.root, "Gagal membuat nilai", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val TYPE_TASKS = "tasks"
        const val TYPE_QUIZ = "quiz"
        const val TYPE_UAS = "uts"
        const val TYPE_UTS = "uas"
        const val SUCCESS_CREATE_GRADE = "request_success_create_grade"
    }
}