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
import com.brazole.peacefulqueens.game.data.MenuDialogCallbacks
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
            onHintClick = viewModel::onHintClick,
            onGameWonDialogDismiss = viewModel::onGameWonDialogDismiss,
            onBestScoresView = viewModel::onBestScoresView,
            onSettingsClick = viewModel::onSettingsClick,
            menuDialogCallbacks = MenuDialogCallbacks(
                onMenuDialogDismiss = viewModel::onMenuDialogDismiss,
                onNewGameConfirmDialogShow = viewModel::onNewGameConfirmDialogShow,
                onNewGameConfirmDialogConfirm = viewModel::onNewGameConfirmDialogConfirm,
                onNewGameConfirmDialogDismiss = viewModel::onNewGameConfirmDialogDismiss
            ),
            onResetClick = viewModel::onResetClick,
            onResetConfirmDialogShow = viewModel::onResetConfirmDialogShow,
            onResetConfirmDialogConfirm = viewModel::onResetConfirmDialogConfirm,
            onResetConfirmDialogDismiss = viewModel::onResetConfirmDialogDismiss
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