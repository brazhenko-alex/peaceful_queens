package com.brazole.peacefulqueens.game.data

class MenuDialogCallbacks(
    val onDismiss: () -> Unit,
    val onConfirmDialogConfirm: (Int) -> Unit,
    val onShowNewGameConfirmDialog: () -> Unit,
    val onConfirmDialogDismiss: () -> Unit
)

