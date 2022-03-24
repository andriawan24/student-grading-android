package com.andriawan.sistempenilaianmahasiswa.di

import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.data.usecase.SignInEmailUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.CreateGradeUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.DeleteGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.EditGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.grades.GetGradesUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.data.usecase.students.*
import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.domain.repository.UserRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.SignInEmailUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.CreateGradeUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.DeleteGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.EditGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.grades.GetGradesUseCase
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.students.*
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providesSignInEmailUseCase(
        userRepository: UserRepository,
        dataStoreRepository: DataStoreRepository,
        errorHandler: ErrorHandler
    ): SignInEmailUseCase {
        return SignInEmailUseCaseImpl(userRepository, dataStoreRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesGetStudentsUseCase(
        studentsRepository: StudentsRepository,
        errorHandler: ErrorHandler
    ): GetStudentsUseCase {
        return GetStudentsUseCaseImpl(studentsRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesGetStudentByNIMUseCase(
        studentsRepository: StudentsRepository,
        errorHandler: ErrorHandler
    ): GetStudentUseCase {
        return GetStudentUseCaseImpl(studentsRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesCreateStudentUseCase(
        studentsRepository: StudentsRepository,
        errorHandler: ErrorHandler
    ): CreateStudentUseCase {
        return CreateStudentUseCaseImpl(studentsRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesEditStudentUseCase(
        studentsRepository: StudentsRepository,
        errorHandler: ErrorHandler
    ): EditStudentUseCase {
        return EditStudentUseCaseImpl(studentsRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesDeleteStudentUseCase(
        studentsRepository: StudentsRepository,
        gradesRepository: GradesRepository,
        errorHandler: ErrorHandler
    ): DeleteStudentUseCase {
        return DeleteStudentUseCaseImpl(studentsRepository, gradesRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesGetGradesUseCase(
        gradesRepository: GradesRepository,
        errorHandler: ErrorHandler
    ): GetGradesUseCase {
        return GetGradesUseCaseImpl(gradesRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesCreateGradesUseCase(
        gradesRepository: GradesRepository,
        errorHandler: ErrorHandler
    ): CreateGradeUseCase {
        return CreateGradeUseCaseImpl(gradesRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesUpdateGradesUseCase(
        gradesRepository: GradesRepository,
        errorHandler: ErrorHandler
    ): EditGradesUseCase {
        return EditGradesUseCaseImpl(gradesRepository, errorHandler)
    }

    @Provides
    @Singleton
    fun providesDeleteGradesUseCase(
        gradesRepository: GradesRepository,
        errorHandler: ErrorHandler
    ): DeleteGradesUseCase {
        return DeleteGradesUseCaseImpl(gradesRepository, errorHandler)
    }
}