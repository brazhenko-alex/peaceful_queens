package com.brazole.peacefulqueens.game.data

import androidx.compose.runtime.Immutable
import com.brazole.peacefulqueens.base.data.UiState
import com.brazole.peacefulqueens.util.Constants
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class GameUiState(
    val boardSize: Int = Constants.BOARD_SIZE_DEFAULT,
    val queensRemaining: Int = boardSize,
    val elapsedTimeFormatted: String = "00:00",
    val cellList: ImmutableList<Cell> = persistentListOf(),
    val isTimerRunning: Boolean = false,
    val showMenuDialog: Boolean = false,
    val showNewGameConfirmDialog: Boolean = false,
    val showGameWonDialog: Boolean = false,
    val showResetConfirmDialog: Boolean = false,
    val hintMode: Boolean = false,
) : UiState