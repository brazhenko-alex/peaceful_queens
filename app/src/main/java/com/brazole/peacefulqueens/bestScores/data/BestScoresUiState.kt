package com.brazole.peacefulqueens.bestScores.data

import androidx.compose.runtime.Immutable
import com.brazole.peacefulqueens.base.data.UiState
import com.brazole.peacefulqueens.util.Constants.NO_ID
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class BestScoresUiState(
    val scores: ImmutableList<BestScore> = persistentListOf(),
    val queensLeft: Int = NO_ID,
    val isLoading: Boolean = false,
    val showClearConfirmationDialog: Boolean = false
) : UiState

