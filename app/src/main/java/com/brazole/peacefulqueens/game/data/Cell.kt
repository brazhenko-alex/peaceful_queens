package com.brazole.peacefulqueens.game.data

import androidx.compose.runtime.Immutable

@Immutable
data class Cell(
    val row: Int,
    val column: Int,
    val hasQueen: Boolean,
    val attacked: Boolean,
    val conflict: Boolean
)

