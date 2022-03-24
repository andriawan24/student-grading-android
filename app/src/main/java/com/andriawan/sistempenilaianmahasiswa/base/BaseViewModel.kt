package com.andriawan.sistempenilaianmahasiswa.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andriawan.sistempenilaianmahasiswa.utils.SingleEvents
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * Base class for the [ViewModel]
 *
 * @author Naufal Fawwaz Andriawan
 */
abstract class BaseViewModel: ViewModel() {

    /**
     * State for showing the toast
     * must be observed by the view that used **[BaseFragment.showToast]**
     */
    protected val _showToast = MutableLiveData<SingleEvents<String>>()
    val showToast: LiveData<SingleEvents<String>> = _showToast
}