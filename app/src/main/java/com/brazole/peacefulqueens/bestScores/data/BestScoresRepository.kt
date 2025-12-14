package com.brazole.peacefulqueens.bestScores.data

import com.brazole.peacefulqueens.bestScores.domain.mapToBestScores
import com.brazole.peacefulqueens.util.PreferencesKeys.KEY_BEST_SCORE_PREFIX
import com.brazole.peacefulqueens.util.PreferencesManager
import com.brazole.peacefulqueens.util.TimeFormatter
import kotlinx.coroutines.delay
import javax.inject.Inject

class BestScoresRepository @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val timeFormatter: TimeFormatter,
) {

    fun saveBestScore(boardSize: Int, timeInSeconds: Long) {
        val key = bestScoreKey(boardSize)
        val currentBest = getBestScore(key)

        if (currentBest == null || timeInSeconds < currentBest) {
            preferencesManager.putLong(key, timeInSeconds)
        }
    }

    suspend fun getAllBestScores(): List<BestScore> {
        val response = getBestScoreFromVeryRemoteRepository()
        val mappedItems = response.mapToBestScores(timeFormatter)
        return mappedItems
    }

    private suspend fun getBestScoreFromVeryRemoteRepository(): List<BestScoreEntity> {
        delay(1500) // Simulating network delay
        val scores = mutableListOf<BestScoreEntity>()
        val existedBestScores = getAllBesScoresKeys()
        existedBestScores.forEach { key ->
            getBestScore(key)?.let { time ->
                val size = key.removePrefix(KEY_BEST_SCORE_PREFIX).toIntOrNull() ?: return@let
                scores.add(
                    BestScoreEntity(
                        boardSize = size,
                        timeInSeconds = time
                    )
                )
            }
        }
        return scores
    }

    suspend fun clearAllBestScores() {
        delay(1000) // Simulating network delay
        getAllBesScoresKeys().forEach {
            preferencesManager.remove(it)
        }
    }

    private fun getBestScore(key: String): Long? {
        val score = preferencesManager.getLong(key, NO_SCORE)
        return if (score == NO_SCORE) null else score
    }

    private fun getAllBesScoresKeys(): List<String> = preferencesManager
        .getAllKeys()
        .filter {
            it.startsWith(KEY_BEST_SCORE_PREFIX)
        }

    private fun bestScoreKey(boardSize: Int): String = "${KEY_BEST_SCORE_PREFIX}$boardSize"

    companion object {
        private const val NO_SCORE = -1L
    }
}

