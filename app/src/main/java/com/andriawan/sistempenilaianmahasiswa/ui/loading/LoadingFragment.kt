package com.andriawan.sistempenilaianmahasiswa.ui.loading

import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.data.repository.DataStoreRepository
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentLoadingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingFragment : BaseFragment<FragmentLoadingBinding, LoadingViewModel>() {
    override val viewModel: LoadingViewModel by viewModels()
    override val binding: FragmentLoadingBinding by lazy {
        FragmentLoadingBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.nightMode.collect {
                when (it) {
                    DataStoreRepository.NIGHT_MODE_NO -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }

                    DataStoreRepository.NIGHT_MODE_YES -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }

                    DataStoreRepository.NIGHT_MODE_SYSTEM_SETTINGS -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }

                delay(3000)

                findNavController().navigate(
                    LoadingFragmentDirections.actionLoadingFragmentToLoginFragment()
                )
            }
        }
    }
}