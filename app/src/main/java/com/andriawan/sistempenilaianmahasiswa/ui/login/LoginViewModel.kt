package com.andriawan.sistempenilaianmahasiswa.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.data.usecase.SignInEmailUseCaseImpl
import com.andriawan.sistempenilaianmahasiswa.domain.usecase.SignInEmailUseCase
import com.andriawan.sistempenilaianmahasiswa.models.response.Response
import com.andriawan.sistempenilaianmahasiswa.utils.SingleEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInEmailUseCase: SignInEmailUseCase,
    private val dataStoreRepository: DataStoreRepository
) : BaseViewModel() {

    private val _dialogState = MutableLiveData<SingleEvents<Pair<String, String>>>()
    val dialogState: LiveData<SingleEvents<Pair<String, String>>> = _dialogState

    private val _signInResult = MutableLiveData<Response<String>>()
    val signInResult: LiveData<Response<String>> = _signInResult

    private val _goToHome = MutableLiveData<SingleEvents<Boolean>>()
    val goToHome: LiveData<SingleEvents<Boolean>> = _goToHome

    init {
        viewModelScope.launch {
            dataStoreRepository.getUser().collect {
                if (it != null) {
                    _goToHome.value = SingleEvents(true)
                }
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val params = SignInEmailUseCaseImpl.Param(email, password)
            signInEmailUseCase.execute(params).collect {
                _signInResult.value = it
            }
        }
    }
}