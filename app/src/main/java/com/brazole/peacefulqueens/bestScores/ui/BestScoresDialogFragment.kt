package com.brazole.peacefulqueens.bestScores.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.data.NavDirectionsWrapper
import com.brazole.peacefulqueens.base.ui.baseComposeView
import com.brazole.peacefulqueens.bestScores.data.BestScoresCallbacks
import com.brazole.peacefulqueens.bestScores.viewModel.BestScoresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BestScoresDialogFragment : DialogFragment() {

    private val viewModel: BestScoresViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val callbacks = BestScoresCallbacks(
            onDismiss = ::dismissDialog,
            onClearAll = viewModel::onClearAll,
            onClearDialogDismiss = viewModel::onClearDialogDismiss,
            onClearDialogConfirm = viewModel::onClearDialogConfirm,
        )

        return baseComposeView(requireContext()) {
            BestScoresDialog(
                uiState = viewModel.getUiStateValue(),
                callbacks = callbacks,
            )
        }
    }

    private fun dismissDialog() {
        findNavController().navigateUp()
    }

    companion object {
        const val ARG_QUEENS_LEFT = "ARG_QUEENS_LEFT"

        fun directions(queenLeft: Int): NavDirectionsWrapper {
            return NavDirectionsWrapper(
                actionId = R.id.bestScoresDialogFragment,
                arguments = Bundle().apply {
                    putInt(ARG_QUEENS_LEFT, queenLeft)
                }
            )
        }
    }
}

