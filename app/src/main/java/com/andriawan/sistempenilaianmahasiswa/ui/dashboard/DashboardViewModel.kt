package com.andriawan.sistempenilaianmahasiswa.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.GetGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.DeleteStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.EditStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.GetStudentsUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.GetGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.DeleteStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.EditStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.GetStudentsUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.User
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getStudentsUseCase: GetStudentsUseCase,
    private val deleteStudentsUseCase: DeleteStudentUseCase,
    dataStoreRepository: DataStoreRepository
): BaseViewModel() {

    private val _studentResults = MutableLiveData<Response<List<Student>?>>()
    val studentResults: LiveData<Response<List<Student>?>> = _studentResults

    private val _studentDeleteResults = MutableLiveData<Response<Nothing>>()
    val studentDeleteResults: LiveData<Response<Nothing>> = _studentDeleteResults

    val user = dataStoreRepository.getUser().asLiveData()

    fun getStudents(query: String? = null) {
        viewModelScope.launch {
            getStudentsUseCase.execute(GetStudentsUseCaseImpl.Param(query)).collect {
                _studentResults.value = it
            }
        }
    }

    fun deleteStudent(studentId: String) {
        viewModelScope.launch {
            val params = DeleteStudentUseCaseImpl.Param(
                studentId = studentId
            )

            deleteStudentsUseCase.execute(params).collect {
                _studentDeleteResults.value = it
            }
        }
    }
}