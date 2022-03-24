package com.andriawan.sistempenilaianmahasiswa.data.usecase

import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.domain.repository.UserRepository
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.SignInEmailUseCase
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorEntity
import com.andriawan.sistempenilaianmahasiswa.models.response.ErrorHandler
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.utils.Helper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class SignInEmailUseCaseImpl(
    private val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val errorHandler: ErrorHandler
) : SignInEmailUseCase {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun execute(params: Param): Flow<Response<String>> = flow {
        emit(Response.Loading)

        try {
            val firebaseUser =
                firebaseAuth.signInWithEmailAndPassword(params.email, params.password).await().user!!

            val getUser = userRepository.getUser(firebaseUser.uid)

            getUser?.let { user ->
                dataStoreRepository.updateUser(Helper.fetchObjectToString(user))
            }

            emit(Response.Success(getUser.toString()))
        } catch (e: FirebaseFirestoreException) {
            emit(Response.Error(errorHandler.getError(e)))
        } catch (e: FirebaseAuthInvalidUserException) {
            emit(Response.Error(ErrorEntity.AccessDenied(Throwable(message = e.message ?: ""))))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            emit(Response.Error(ErrorEntity.AccessDenied(Throwable(message = e.message ?: ""))))
        }
    }.flowOn(Dispatchers.IO)

    data class Param(
        var email: String,
        var password: String
    )
}