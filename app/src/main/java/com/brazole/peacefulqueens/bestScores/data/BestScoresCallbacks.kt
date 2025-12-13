package com.brazole.peacefulqueens.bestScores.data

class BestScoresCallbacks(
    val onDismiss: () -> Unit,
    val onClearAll: () -> Unit,

    val onClearDialogDismiss: () -> Unit,
    val onClearDialogConfirm: () -> Unit,
)

