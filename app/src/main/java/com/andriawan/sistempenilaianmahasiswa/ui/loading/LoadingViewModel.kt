package com.andriawan.sistempenilaianmahasiswa.ui.loading

import com.andriawan.sistempenilaianmahasiswa.base.BaseViewModel
import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.utils.SingleEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    dataStoreRepository: DataStoreRepository
): BaseViewModel() {

    fun showToast() {
        _showToast.value = SingleEvents("Hello World")
    }

    val nightMode = dataStoreRepository.getNightMode()
}