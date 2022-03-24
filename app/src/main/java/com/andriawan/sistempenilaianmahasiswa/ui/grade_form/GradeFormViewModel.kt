package com.andriawan.sistempenilaianmahasiswa.ui.grade_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.CreateGradeUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.EditGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.GetGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.CreateGradeUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.EditGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.GetGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.ui.detail.DetailFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradeFormViewModel @Inject constructor(
    private val createGradeUseCase: CreateGradeUseCase,
    private val editGradesUseCase: EditGradesUseCase,
    private val getGradesUseCase: GetGradesUseCase
) : BaseViewModel() {

    private val _createGradesResponse = MutableLiveData<Response<Nothing>>()
    val createGradesResponse: LiveData<Response<Nothing>> = _createGradesResponse

    private val _getGradeResponse = MutableLiveData<Response<List<Grades>>>()
    val getGradeResponse: LiveData<Response<List<Grades>>> = _getGradeResponse

    private val user = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    fun getGrade(studentId: String, subject: String? = null) {
        viewModelScope.launch {
            val param = GetGradesUseCaseImpl.Param(
                studentId = studentId,
                subject = subject
            )

            getGradesUseCase.execute(param).collect {
                _getGradeResponse.value = it
            }
        }
    }

    fun saveGrades(
        studentId: String,
        subject: String,
        tasksGrade: String,
        quizGrade: String,
        utsGrade: String,
        uasGrade: String
    ) {
        viewModelScope.launch {
            val param = CreateGradeUseCaseImpl.Param(
                studentId = studentId,
                teacherId = user,
                subject = subject,
                tasksGrade = tasksGrade.toInt(),
                quizGrade = quizGrade.toInt(),
                utsGrade = utsGrade.toInt(),
                uasGrade = uasGrade.toInt()
            )

            createGradeUseCase.execute(param).collect {
                _createGradesResponse.value = it
            }
        }
    }

    fun editGrades(
        studentId: String,
        subject: String,
        tasksGrade: String,
        quizGrade: String,
        utsGrade: String,
        uasGrade: String
    ) {
        viewModelScope.launch {
            val grades = listOf(
                Grades(
                    subject = subject,
                    teacherId = user,
                    studentId = studentId,
                    grade = tasksGrade.toInt(),
                    type = DetailFragment.TYPE_TASKS,
                    id = ""
                ),
                Grades(
                    subject = subject,
                    teacherId = user,
                    studentId = studentId,
                    grade = quizGrade.toInt(),
                    type = DetailFragment.TYPE_QUIZ,
                    id = ""
                ),
                Grades(
                    subject = subject,
                    teacherId = user,
                    studentId = studentId,
                    grade = utsGrade.toInt(),
                    type = DetailFragment.TYPE_UTS,
                    id = ""
                ),
                Grades(
                    subject = subject,
                    teacherId = user,
                    studentId = studentId,
                    grade = uasGrade.toInt(),
                    type = DetailFragment.TYPE_UAS,
                    id = ""
                ),
            )

            val editUseCaseParam = EditGradesUseCaseImpl.Param(
                grades,
                true
            )

            editGradesUseCase.execute(editUseCaseParam).collect {
                _createGradesResponse.value = it
            }
        }
    }
}