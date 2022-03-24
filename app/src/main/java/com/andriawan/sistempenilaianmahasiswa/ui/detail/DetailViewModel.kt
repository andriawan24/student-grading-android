package com.andriawan.sistempenilaianmahasiswa.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.DeleteGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.GetGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.GetStudentUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.DeleteGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.GetGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.GetStudentUseCase
import com.andriawan.sistempenilaianmahasiswa.models.Grades
import com.andriawan.sistempenilaianmahasiswa.models.Student
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getStudentUseCase: GetStudentUseCase,
    private val getGradesUseCase: GetGradesUseCase,
    private val deleteGradesUseCase: DeleteGradesUseCase
) : BaseViewModel() {

    private val _studentResponse = MutableLiveData<Response<Student?>>()
    val studentResponse: LiveData<Response<Student?>> = _studentResponse

    private val _gradeDeleteResponse = MutableLiveData<Response<Nothing>>()
    val gradeDeleteResponse: LiveData<Response<Nothing>> = _gradeDeleteResponse

    private val _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    private val _studentGrades = MutableLiveData<Pair<String, Double>>()
    val studentGrades: LiveData<Pair<String, Double>> = _studentGrades

    private val _gradeDetail = MutableLiveData<List<Pair<String, Pair<String, Double>>>>()
    val gradeDetail: LiveData<List<Pair<String, Pair<String, Double>>>> = _gradeDetail

    fun getStudent(studentId: String) {
        viewModelScope.launch {
            val params = GetStudentUseCaseImpl.Param(studentId)

            getStudentUseCase.execute(params).collect {
                _studentResponse.value = it

                if (it is Response.Success) {
                    it.data?.let { students ->
                        _student.value = students
                    }
                }
            }
        }
    }

    fun getGrades(studentId: String) {
        viewModelScope.launch {
            val param = GetGradesUseCaseImpl.Param(
                studentId = studentId
            )

            getGradesUseCase.execute(param).collect {
                when (it) {
                    Response.Loading -> {
                        // Do nothing
                    }

                    is Response.Success -> {
                        it.data?.let { gradeList ->
                            val mappedGrades = calculateTotalGrades(gradeList)
                            val mappedGradesBySubject: Map<String, Double> =
                                calculateTotalGradesBySubject(gradeList)

                            val totalScore = mappedGrades.values.sum()

                            val scoreText: String = getScoreText(totalScore)
                            Timber.d("Total score is $totalScore Score Text is $scoreText $mappedGrades Mapped by subject $mappedGradesBySubject")

                            _studentGrades.value = Pair(scoreText, totalScore)

                            val listMappedBySubject = mutableListOf<Pair<String, Pair<String, Double>>>()
                            mappedGradesBySubject.entries.forEach { entry ->
                                val mappedBySubject: Pair<String, Pair<String, Double>> =
                                    Pair(entry.key, Pair(getScoreText(entry.value), entry.value))
                                listMappedBySubject.add(mappedBySubject)
                            }

                            _gradeDetail.value = listMappedBySubject
                        }
                    }

                    is Response.Error -> {
                        Timber.e("Error grade ${it.error?.originalException?.message}")
                    }
                }
            }
        }
    }

    private fun calculateTotalGradesBySubject(gradeList: List<Grades>): Map<String, Double> {
        val gradeGrouped = gradeList.groupBy { grade -> grade.subject }
        val mappedGrades: MutableMap<String, Double> = mutableMapOf()

        gradeGrouped.forEach { group ->

            val totalTask: Double =
                group.value.filter { grade -> grade.type == DetailFragment.TYPE_TASKS }
                    .sumOf { grade -> grade.grade?.toInt() ?: 0 } * (10.0 / 100.0)

            Timber.d("Total task $totalTask ${group.value.filter { grade -> grade.type == DetailFragment.TYPE_TASKS }}")

            val totalQuiz: Double =
                group.value.filter { grade -> grade.type == DetailFragment.TYPE_QUIZ }
                    .sumOf { grade -> grade.grade?.toInt() ?: 0 } * (20.0 / 100.0)

            val totalUts: Double =
                group.value.filter { grade -> grade.type == DetailFragment.TYPE_UTS }
                    .sumOf { grade -> grade.grade?.toInt() ?: 0 } * (30.0 / 100.0)

            val totalUas: Double =
                group.value.filter { grade -> grade.type == DetailFragment.TYPE_UAS }
                    .sumOf { grade -> grade.grade?.toInt() ?: 0 } * (40.0 / 100.0)

            val totalAverageTaskGrade: Double =
                (totalTask + totalQuiz + totalUts + totalUas)

            mappedGrades[group.key.toString()] =
                totalAverageTaskGrade
        }

        return mappedGrades
    }

    private fun getScoreText(totalScore: Double): String {
        return when {
            totalScore > 92 -> {
                "A"
            }

            totalScore in 86.0..91.0 -> {
                "A-"
            }

            totalScore in 81.0..85.0 -> {
                "B+"
            }

            totalScore in 76.0..80.0 -> {
                "B"
            }

            totalScore in 71.0..75.0 -> {
                "B-"
            }

            totalScore in 66.0..70.0 -> {
                "C+"
            }

            totalScore in 60.0..65.0 -> {
                "C"
            }

            totalScore in 55.0..59.0 -> {
                "D"
            }

            else -> {
                "E"
            }
        }
    }

    private fun calculateTotalGrades(gradeList: List<Grades>): Map<String, Double> {
        val gradeGrouped = gradeList.groupBy { grade -> grade.type }
        val mappedGrades: MutableMap<String, Double> = mutableMapOf()
        mappedGrades[DetailFragment.TYPE_TASKS] = 0.0
        mappedGrades[DetailFragment.TYPE_QUIZ] = 0.0
        mappedGrades[DetailFragment.TYPE_UTS] = 0.0
        mappedGrades[DetailFragment.TYPE_UAS] = 0.0

        gradeGrouped.forEach { group ->
            when (group.key) {
                DetailFragment.TYPE_TASKS -> {
                    val totalTaskGrade = group.value.sumOf { grade ->
                        grade.grade?.toInt() ?: 0
                    }.toDouble()

                    val totalAverageTaskGrade: Double =
                        totalTaskGrade * (10.0 / 100.0)

                    mappedGrades[DetailFragment.TYPE_TASKS] =
                        totalAverageTaskGrade / group.value.count()
                }
                DetailFragment.TYPE_QUIZ -> {
                    val totalTaskGrade = group.value.sumOf { grade ->
                        grade.grade?.toInt() ?: 0
                    }.toDouble()

                    val totalAverageTaskGrade: Double =
                        totalTaskGrade * (20.0 / 100.0)

                    mappedGrades[DetailFragment.TYPE_QUIZ] =
                        totalAverageTaskGrade / group.value.count()
                }
                DetailFragment.TYPE_UTS -> {
                    val totalTaskGrade = group.value.sumOf { grade ->
                        grade.grade?.toInt() ?: 0
                    }.toDouble()

                    val totalAverageTaskGrade: Double =
                        totalTaskGrade * (30.0 / 100.0)

                    mappedGrades[DetailFragment.TYPE_UTS] =
                        totalAverageTaskGrade / group.value.count()
                }
                DetailFragment.TYPE_UAS -> {
                    val totalTaskGrade = group.value.sumOf { grade ->
                        grade.grade?.toInt() ?: 0
                    }.toDouble()

                    val totalAverageTaskGrade: Double =
                        totalTaskGrade * (40.0 / 100.0)

                    mappedGrades[DetailFragment.TYPE_UAS] =
                        totalAverageTaskGrade / group.value.count()
                }
            }
        }

        return mappedGrades
    }

    fun deleteGrades(studentId: String, subject: String) {
        viewModelScope.launch {
            val param = DeleteGradesUseCaseImpl.Param(studentId, subject)

            deleteGradesUseCase.execute(param).collect {
                _gradeDeleteResponse.value = it
            }
        }
    }
}