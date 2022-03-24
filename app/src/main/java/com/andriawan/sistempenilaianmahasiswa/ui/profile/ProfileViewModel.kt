package com.andriawan.sistempenilaianmahasiswa.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.utils.SingleEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): BaseViewModel() {

    val profile = dataStoreRepository.getUser().asLiveData()

    private val _goToHome = MutableLiveData<SingleEvents<Boolean>>()
    val goToHome: LiveData<SingleEvents<Boolean>> = _goToHome

    fun logout() {
        viewModelScope.launch {
            dataStoreRepository.updateUser("")
            _goToHome.value = SingleEvents(true)
        }
    }
}