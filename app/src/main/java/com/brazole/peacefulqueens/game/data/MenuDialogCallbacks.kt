package com.brazole.peacefulqueens.game.data

class MenuDialogCallbacks(
    val onMenuDialogDismiss: () -> Unit,
    val onNewGameConfirmDialogShow: () -> Unit,
    val onNewGameConfirmDialogConfirm: (Int) -> Unit,
    val onNewGameConfirmDialogDismiss: () -> Unit
)

