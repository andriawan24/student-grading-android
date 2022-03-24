package com.andriawan.sistempenilaianmahasiswa.di

import com.andriawan.sistempenilaianmahasiswa.data.mapper.students.StudentListDataMapper
import com.andriawan.sistempenilaianmahasiswa.data.mapper.UserDataMapper
import com.andriawan.sistempenilaianmahasiswa.data.mapper.grades.GradeDataMapper
import com.andriawan.sistempenilaianmahasiswa.data.mapper.grades.GradesDataMapper
import com.andriawan.sistempenilaianmahasiswa.data.mapper.students.StudentDataMapper
import com.andriawan.sistempenilaianmahasiswa.data.repository.GradesRepositoryImpl
import com.andriawan.sistempenilaianmahasiswa.data.repository.StudentsRepositoryImpl
import com.andriawan.sistempenilaianmahasiswa.data.repository.UserRepositoryImpl
import com.andriawan.sistempenilaianmahasiswa.domain.repository.GradesRepository
import com.andriawan.sistempenilaianmahasiswa.domain.repository.StudentsRepository
import com.andriawan.sistempenilaianmahasiswa.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesUserRepository(): UserRepository {
        return UserRepositoryImpl(UserDataMapper())
    }

    @Provides
    @Singleton
    fun providesStudentRepository(): StudentsRepository {
        return StudentsRepositoryImpl(StudentListDataMapper(), StudentDataMapper())
    }

    @Provides
    @Singleton
    fun providesGradesRepository(): GradesRepository {
        return GradesRepositoryImpl(GradesDataMapper(), GradeDataMapper())
    }
}