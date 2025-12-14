package com.brazole.peacefulqueens.game.data

class GameCallbacks(
    val onCellClick: (Cell) -> Unit,
    val onHintClick: () -> Unit,
    val onGameWonDialogDismiss: () -> Unit,
    val onBestScoresView: () -> Unit,
    val onSettingsClick: () -> Unit,
    val menuDialogCallbacks: MenuDialogCallbacks,
    val onResetClick: () -> Unit,
    val onResetConfirmDialogShow: () -> Unit,
    val onResetConfirmDialogConfirm: () -> Unit,
    val onResetConfirmDialogDismiss: () -> Unit
)

