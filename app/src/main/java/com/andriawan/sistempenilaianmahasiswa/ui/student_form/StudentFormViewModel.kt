package com.andriawan.sistempenilaianmahasiswa.ui.student_form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.CreateGradeUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.CreateStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.EditStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.CreateStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.EditStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentFormViewModel @Inject constructor(
    private val createStudentUseCase: CreateStudentUseCase,
    private val editStudentUseCase: EditStudentUseCase
) : BaseViewModel() {

    private val _createStudentResponse = MutableLiveData<Response<Nothing>>()
    val createStudentResponse: LiveData<Response<Nothing>> = _createStudentResponse

    private val _studentEditResults = MutableLiveData<Response<Nothing>>()
    val studentEditResults: LiveData<Response<Nothing>> = _studentEditResults

    fun saveStudent(
        name: String,
        nim: String,
        entryYear: String,
        major: String,
        isEdit: Boolean,
    ) {
        if (!isEdit) {
            viewModelScope.launch {
                val param = CreateStudentUseCaseImpl.Param(
                    nim, name, entryYear, major
                )

                createStudentUseCase.execute(param).collect {
                    _createStudentResponse.value = it
                }
            }
        } else {
            viewModelScope.launch {
                val params = EditStudentUseCaseImpl.Param(
                    Student(
                        name = name,
                        nim = nim,
                        major = major,
                        entryYear = entryYear,
                        phoneNumber = ""
                    )
                )

                editStudentUseCase.execute(params).collect {
                    _studentEditResults.value = it
                }
            }
        }
    }
}