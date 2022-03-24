package com.andriawan.sistempenilaianmahasiswa.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Base class for fragment in purpose to reduce
 * Boilerplate code for [ViewBinding] and [ViewModel] classes
 * Extends [Fragment]
 *
 * @param VB - [ViewBinding] class for abstract variable
 * @param VM - [ViewModel] class for abstract variable
 *
 * @author Naufal Fawwaz Andriawan
 */
abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {
    abstract val viewModel: VM
    abstract val binding: VB

    // Initialized state
    private var hasInitialized = false

    /**
     * When view is being created
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    /**
     * When view has been created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if the fragment has initialized to prevent duplicated calls
        if (!hasInitialized) {
            onInitViews()
            onInitObservers()
            onClickListener()
        }
    }

    /**
     * Used for initialized views
     */
    protected open fun onInitViews() = Unit

    /**
     * Used for init livedata observer
     */
    protected open fun onInitObservers() = Unit

    /**
     * Used for defining click listener on view
     */
    protected open fun onClickListener() = Unit

    /**
     * Shows toast with a message
     *
     * @param message - The content of the message
     */
    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Run when fragment on pause state
     * Or when the view is not showing on the app
     */
    override fun onPause() {
        super.onPause()
        hasInitialized = true
    }
}