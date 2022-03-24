package com.andriawan.sistempenilaianmahasiswa.ui.detail.grade_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.EditGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.GetGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.EditGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.GetGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GradeDetailViewModel @Inject constructor(
    private val getGradesUseCase: GetGradesUseCase,
    private val editGradesUseCase: EditGradesUseCase
) : BaseViewModel() {

    private val _gradesResponse = MutableLiveData<Response<List<Grades>>>()
    val gradesResponse: LiveData<Response<List<Grades>>> = _gradesResponse

    private val _gradesEditResponse = MutableLiveData<Response<Nothing>>()
    val gradesEditResponse: LiveData<Response<Nothing>> = _gradesEditResponse

    private val _gradeDetail = MutableLiveData<Pair<Double, Double>>()
    val gradeDetail: LiveData<Pair<Double, Double>> = _gradeDetail

    fun getGrades(type: String, studentId: String) {
        viewModelScope.launch {
            val param = GetGradesUseCaseImpl.Param(
                studentId = studentId,
                type = type
            )

            getGradesUseCase.execute(param).collect {
                _gradesResponse.value = it

                if (it is Response.Success) {
                    it.data?.let { gradeList ->
                        val totalScore = gradeList.sumOf { grade -> grade.grade?.toInt() ?: 0 }
                        val totalAverage = totalScore.toDouble() / gradeList.count().toDouble()

                        _gradeDetail.value = Pair(totalScore.toDouble(), totalAverage)
                    }
                }
            }
        }
    }

    fun editGrade(grades: Grades) {
        viewModelScope.launch {
            val param = EditGradesUseCaseImpl.Param(
                grades = listOf(grades)
            )

            editGradesUseCase.execute(param).collect {
                _gradesEditResponse.value = it
            }
        }
    }
}