package com.brazole.peacefulqueens.game.data

class GameCallbacks(
    val onCellClick: (Cell) -> Unit,
    val onResetClick: () -> Unit,
    val onHintClick: () -> Unit,
    val onWinDismiss: () -> Unit,
    val onViewBestScores: () -> Unit,
    val onSettingsClick: () -> Unit,
    val onMenuDismiss: () -> Unit,
    val onNewBoardSizeConfirm: (Int) -> Unit,
    val onShowNewGameConfirmDialog: () -> Unit,
    val onDismissConfirmDialog: () -> Unit,
    val onShowResetConfirmDialog: () -> Unit,
    val onResetConfirm: () -> Unit,
    val onDismissResetConfirmDialog: () -> Unit
)

