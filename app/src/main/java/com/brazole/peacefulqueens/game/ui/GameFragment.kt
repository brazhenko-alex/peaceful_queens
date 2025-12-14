package com.brazole.peacefulqueens.game.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.brazole.peacefulqueens.base.data.Event
import com.brazole.peacefulqueens.base.ui.BaseComposeFragment
import com.brazole.peacefulqueens.base.ui.baseComposeView
import com.brazole.peacefulqueens.bestScores.ui.BestScoresDialogFragment
import com.brazole.peacefulqueens.game.data.GameCallbacks
import com.brazole.peacefulqueens.game.data.GameUiState
import com.brazole.peacefulqueens.game.data.ShowBestScoresDialog
import com.brazole.peacefulqueens.game.viewModel.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameFragment() : BaseComposeFragment<GameViewModel, GameUiState>() {

    override val viewModel: GameViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.resumeTimerIfNeeded()

        val callBacks = GameCallbacks(
            onCellClick = viewModel::onCellClick,
            onResetClick = viewModel::onResetGame,
            onWinDismiss = viewModel::onWinDismiss,
            onViewBestScores = viewModel::onViewBestScores,
            onSettingsClick = viewModel::onSettingsClick,
            onMenuDismiss = viewModel::onMenuDismiss,
            onNewBoardSizeConfirm = viewModel::onNewBoardSizeConfirm,
            onShowNewGameConfirmDialog = viewModel::onShowConfirmDialog,
            onDismissConfirmDialog = viewModel::onDismissConfirmDialog,
            onHintClick = viewModel::onHintClick,
            onShowResetConfirmDialog = viewModel::onShowResetConfirmDialog,
            onResetConfirm = viewModel::onResetConfirm,
            onDismissResetConfirmDialog = viewModel::onDismissResetConfirmDialog
        )

        return baseComposeView(requireContext()) {
            GameCompose(
                uiState = viewModel.getUiStateValue(),
                callbacks = callBacks
            )
        }
    }

    override fun consumeEvents(event: Event) {
        super.consumeEvents(event)
        when (event) {
            is ShowBestScoresDialog -> {
                findNavController().navigate(BestScoresDialogFragment.directions(event.queensLeft))
            }
        }
    }
}