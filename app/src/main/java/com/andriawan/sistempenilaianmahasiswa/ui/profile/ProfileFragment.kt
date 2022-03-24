package com.andriawan.sistempenilaianmahasiswa.ui.profile

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.andriawan.sistempenilaianmahasiswa.base.BaseFragment
import com.andriawan.sistempenilaianmahasiswa.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()
    override val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onInitViews() {
        super.onInitViews()

        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()
            viewModel.logout()
        }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()

        viewModel.profile.observe(this) {
            it?.let { user ->
                binding.usernameTextView.text = user.name
            }
        }

        viewModel.goToHome.observe(this) {
            it.getContentIfNotHandled()?.let {
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
                )
            }
        }
    }
}