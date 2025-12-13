package com.brazole.peacefulqueens.base.ui

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.brazole.peacefulqueens.base.data.Event
import com.brazole.peacefulqueens.base.data.NavigateBack
import com.brazole.peacefulqueens.base.data.ShowMessage
import com.brazole.peacefulqueens.base.data.ShowMessageRes
import com.brazole.peacefulqueens.base.data.UiState
import com.brazole.peacefulqueens.base.viewModel.BaseViewModel
import com.brazole.peacefulqueens.util.extensions.launchAndCollect
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

abstract class BaseComposeFragment<VM : BaseViewModel<State>, State : UiState> : Fragment() {

    protected abstract val viewModel: VM

    override fun onResume() {
        super.onResume()
        viewModel.events
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .launchAndCollect(viewLifecycleOwner.lifecycleScope, ::consumeEvents)

    }

    open fun consumeEvents(event: Event) {
        when (event) {
            is ShowMessage -> showMessage(event.message)
            is ShowMessageRes -> showMessage(getString(event.message))
            is NavigateBack -> findNavController().popBackStack()
        }
    }

    private fun showMessage(message: String) {
        activity?.runOnUiThread {
            if (!isAdded) return@runOnUiThread
            try {
                val root = requireActivity().findViewById<View>(android.R.id.content)
                Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
            } catch (e: Exception) {
                Timber.d("BaseComposeFragment. ShowMessage, Failed to show Snackbar. Error: ${e.message}")
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

