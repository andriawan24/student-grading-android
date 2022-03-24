package com.andriawan.sistempenilaianmahasiswa.ui.detail.grade_detail

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.andriawan.sistempenilaianmahasiswa.R
import com.andriawan.sistempenilaianmahasiswa.adapters.GradeAdapter
import com.andriawan.sistempenilaianmahasiswa.adapters.StudentAdapter
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentGradeDetailBinding
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.ui.detail.DetailFragment
import com.andriawan.sistempenilaianmahasiswa.utils.DialogEditGradeHelper
import com.andriawan.sistempenilaianmahasiswa.utils.DialogHelper
import com.andriawan.sistempenilaianmahasiswa.utils.hide
import com.andriawan.sistempenilaianmahasiswa.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GradeDetailFragment : BaseFragment<FragmentGradeDetailBinding, GradeDetailViewModel>() {
    override val viewModel: GradeDetailViewModel by viewModels()
    override val binding: FragmentGradeDetailBinding by lazy {
        FragmentGradeDetailBinding.inflate(layoutInflater)
    }

    private val args: GradeDetailFragmentArgs by navArgs()
    private lateinit var adapter: GradeAdapter
    private lateinit var dialogEditGradeHelper: DialogEditGradeHelper

    override fun onInitViews() {
        super.onInitViews()
        initDialog()
        initToolbar()
        initRecycler()

        viewModel.getGrades(args.gradeType, args.studentId)
    }

    private fun initDialog() {
        dialogEditGradeHelper = DialogEditGradeHelper(requireContext(), layoutInflater)
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        when (args.gradeType) {
            DetailFragment.TYPE_TASKS -> {
                binding.titleToolbar.text = getString(R.string.nilai_tugas)
            }
            DetailFragment.TYPE_QUIZ -> {
                binding.titleToolbar.text = getString(R.string.nilai_quiz)
            }
            DetailFragment.TYPE_UTS -> {
                binding.titleToolbar.text = getString(R.string.nilai_uts)
            }
            DetailFragment.TYPE_UAS -> {
                binding.titleToolbar.text = getString(R.string.nilai_uas)
            }
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.gradeDetail.observe(this) {
            binding.avgGradeTextView.text = "Total rata-rata : ${String.format("%.2f", it.second)}"
            binding.totalGradeTextView.text = "Total nilai : ${it.first}"
        }

        viewModel.gradesEditResponse.observe(this) {
            when (it) {
                Response.Loading -> {
                    Timber.d("Loading")
                }

                is Response.Success -> {
                    setFragmentResult(
                        DetailFragment.SUCCESS_CREATE_GRADE,
                        bundleOf(Pair("success", true))
                    )
                    showToast("Berhasil mengubah nilai")
                    viewModel.getGrades(args.gradeType, args.studentId)
                }

                is Response.Error -> {
                    Timber.e("Error grades ${it.error?.originalException?.message}")
                }
            }
        }

        viewModel.gradesResponse.observe(this) {
            when (it) {
                Response.Loading -> {
                    Timber.d("Loading")
                }

                is Response.Success -> {
                    it.data?.let { grades ->
                        adapter.setData(grades)

                        if (grades.isEmpty()) {
                            binding.gradeRecyclerView.hide()
                            binding.emptyLayout.root.visible()
                        } else {
                            binding.gradeRecyclerView.visible()
                            binding.emptyLayout.root.hide()
                        }
                    }
                }

                is Response.Error -> {
                    Timber.e("Error grades ${it.error?.originalException?.message}")
                }
            }
        }
    }

    private fun initRecycler() {
        adapter = GradeAdapter { grade ->
            dialogEditGradeHelper.updateState(
                Pair(DialogEditGradeHelper.SHOW, ""),
                grade
            ) { grades ->
                dialogEditGradeHelper.dismiss()
                viewModel.editGrade(grades)
            }
        }

        binding.gradeRecyclerView.adapter = adapter
        binding.gradeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}