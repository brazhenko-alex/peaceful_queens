package com.brazole.peacefulqueens.bestScores.data

import androidx.compose.runtime.Immutable

@Immutable
data class BestScore(
    val boardSize: Int,
    val time: String,
)

