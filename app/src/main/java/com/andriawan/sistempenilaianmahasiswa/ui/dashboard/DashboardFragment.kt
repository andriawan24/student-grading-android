package com.andriawan.sistempenilaianmahasiswa.ui.dashboard

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.andriawan.sistempenilaianmahasiswa.adapters.StudentAdapter
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentDashboardBinding
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding, DashboardViewModel>() {
    override val viewModel: DashboardViewModel by viewModels()
    override val binding: FragmentDashboardBinding by lazy {
        FragmentDashboardBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: StudentAdapter
    private lateinit var dialogEditEditDeleteStudentHelper: DialogEditDeleteStudentHelper
    private lateinit var dialogHelper: DialogHelper

    override fun onInitViews() {
        super.onInitViews()
        initDialog()
        initRecycler()

        viewModel.getStudents()
        setFragmentResultListener(SUCCESS_CREATE_STUDENT) { _, b ->
            if (b.getBoolean("success")) {
                viewModel.getStudents()
                Snackbar.make(
                    binding.root,
                    "Berhasil menambah mahasiswa",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("OK") { }.show()
            }
        }

        setFragmentResultListener(SUCCESS_UPDATE_STUDENT) { _, b ->
            if (b.getBoolean("success")) {
                viewModel.getStudents()
                Snackbar.make(
                    binding.root,
                    "Berhasil mengubah mahasiswa",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("OK") { }.show()
            }
        }
    }

    private fun initDialog() {
        dialogEditEditDeleteStudentHelper = DialogEditDeleteStudentHelper(requireContext(), layoutInflater)
        dialogHelper = DialogHelper(requireContext(), layoutInflater)
    }

    private fun initRecycler() {
        adapter = StudentAdapter(
            { student ->
                findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(student.nim!!)
                )
            }, {
                dialogEditEditDeleteStudentHelper.updateState(
                    Pair(DialogEditDeleteStudentHelper.SHOW, ""),
                    it,
                    { student ->
                        dialogEditEditDeleteStudentHelper.dismiss()
                        viewModel.deleteStudent(student.nim!!)
                    },
                    { student ->
                        dialogEditEditDeleteStudentHelper.dismiss()
                        findNavController().navigate(
                            DashboardFragmentDirections.actionDashboardFragmentToStudentFormFragment(
                                isEdit = true,
                                student = student
                            )
                        )
                    }
                )
            }
        )

        binding.studentListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.studentListRecyclerView.adapter = adapter
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.studentDeleteResults.observe(this) {
            when (it) {
                Response.Loading -> {
                    Timber.d("Loading")
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    viewModel.getStudents()
                    dialogHelper.dismiss()
                    Snackbar.make(
                        binding.root,
                        "Berhasil menghapus mahasiswa",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("OK") { }.show()
                }

                is Response.Error -> {
                    Timber.e("Error students ${it.error?.originalException?.message}")
                }
            }
        }

        viewModel.studentResults.observe(this) {
            when (it) {
                Response.Loading -> {
                    Timber.d("Loading")
                    dialogHelper.updateState(Pair(DialogHelper.LOADING, ""))
                }

                is Response.Success -> {
                    it.data?.let { students ->
                        adapter.setData(students)
                        dialogHelper.dismiss()

                        if (students.isEmpty()) {
                            binding.studentListRecyclerView.hide()
                            binding.emptyLayout.root.visible()
                        } else {
                            binding.studentListRecyclerView.visible()
                            binding.emptyLayout.root.hide()
                        }
                    }
                }

                is Response.Error -> {
                    Timber.e("Error students ${it.error?.originalException?.message}")
                }
            }
        }

        viewModel.user.observe(this) {
            it?.let { user ->
                binding.sayHiTextView.text = "Hello, ${user.name}"
            }
        }
    }

    override fun onClickListener() {
        super.onClickListener()

        binding.addGradeButton.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToStudentFormFragment()
            )
        }

        binding.profileImageView.setOnClickListener {
            findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToProfileFragment()
            )
        }

        binding.searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getStudents(binding.searchEditText.text.toString().trim())
                binding.searchEditText.setText("")
                requireContext().hideKeyboard(binding.searchEditText)

                return@setOnEditorActionListener true
            }

            false
        }
    }

    companion object {
        const val SUCCESS_CREATE_STUDENT = "request_create_student_success"
        const val SUCCESS_UPDATE_STUDENT = "request_update_student_success"
    }
}