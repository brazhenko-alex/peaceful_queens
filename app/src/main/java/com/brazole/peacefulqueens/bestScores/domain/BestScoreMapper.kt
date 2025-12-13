package com.brazole.peacefulqueens.bestScores.domain

import com.brazole.peacefulqueens.bestScores.data.BestScore
import com.brazole.peacefulqueens.bestScores.data.BestScoreEntity
import com.brazole.peacefulqueens.util.TimeFormatter

fun List<BestScoreEntity>.mapToBestScores(timeFormatter: TimeFormatter): List<BestScore> {
    return this.map { it.mapToBestScore(timeFormatter) }
}

fun BestScoreEntity.mapToBestScore(timeFormatter: TimeFormatter): BestScore {
    val time = timeFormatter.formatTime(timeInSeconds)
    return BestScore(
        boardSize = this.boardSize,
        time = time
    )
}