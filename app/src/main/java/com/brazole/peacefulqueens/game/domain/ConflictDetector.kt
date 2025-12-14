package com.brazole.peacefulqueens.game.domain

import javax.inject.Inject
import kotlin.math.abs

class ConflictDetector @Inject constructor() {

    fun isAttacking(
        queenRow: Int,
        queenColumn: Int,
        targetRow: Int,
        targetColumn: Int
    ): Boolean {
        if (queenRow == targetRow && queenColumn == targetColumn) {
            return false
        }

        return queenRow == targetRow ||
                queenColumn == targetColumn ||
                abs(queenRow - targetRow) == abs(queenColumn - targetColumn)
    }

}

