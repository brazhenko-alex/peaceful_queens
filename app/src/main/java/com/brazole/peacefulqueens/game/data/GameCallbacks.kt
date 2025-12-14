package com.brazole.peacefulqueens.game.data

class GameCallbacks(
    val onCellClick: (Cell) -> Unit,
    val onResetClick: () -> Unit,
    val onHintClick: () -> Unit,
    val onWinDismiss: () -> Unit,
    val onViewBestScores: () -> Unit,
    val onSettingsClick: () -> Unit,
    val menuDialogCallbacks: MenuDialogCallbacks,
    val onShowResetConfirmDialog: () -> Unit,
    val onResetConfirm: () -> Unit,
    val onDismissResetConfirmDialog: () -> Unit
)

