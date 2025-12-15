package com.brazole.peacefulqueens.bestScores.data

import com.brazole.peacefulqueens.util.PreferencesKeys
import com.brazole.peacefulqueens.util.PreferencesManager
import com.brazole.peacefulqueens.util.TimeFormatter
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BestScoresRepositoryTest {

    @MockK
    private lateinit var preferencesManager: PreferencesManager

    @MockK
    private lateinit var timeFormatter: TimeFormatter

    private lateinit var repository: BestScoresRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = BestScoresRepository(preferencesManager, timeFormatter)
    }

    @Test
    fun `saveBestScore saves new score when no previous score exists`() {
        val boardSize = 8
        val timeInSeconds = 120L
        val key = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize"

        every { preferencesManager.getLong(key, -1L) } returns -1L
        every { preferencesManager.putLong(key, timeInSeconds) } just Runs

        repository.saveBestScore(boardSize, timeInSeconds)

        verify { preferencesManager.putLong(key, timeInSeconds) }
    }

    @Test
    fun `saveBestScore saves new score when better than previous score`() {
        val boardSize = 8
        val timeInSeconds = 100L
        val previousBest = 120L
        val key = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize"

        every { preferencesManager.getLong(key, -1L) } returns previousBest
        every { preferencesManager.putLong(key, timeInSeconds) } just Runs

        repository.saveBestScore(boardSize, timeInSeconds)

        verify { preferencesManager.putLong(key, timeInSeconds) }
    }

    @Test
    fun `saveBestScore does not save when new score is worse than previous score`() {
        val boardSize = 8
        val timeInSeconds = 150L
        val previousBest = 120L
        val key = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize"

        every { preferencesManager.getLong(key, -1L) } returns previousBest

        repository.saveBestScore(boardSize, timeInSeconds)

        verify(exactly = 0) { preferencesManager.putLong(any(), any()) }
    }

    @Test
    fun `saveBestScore does not save when new score equals previous score`() {
        val boardSize = 8
        val timeInSeconds = 120L
        val previousBest = 120L
        val key = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize"

        every { preferencesManager.getLong(key, -1L) } returns previousBest

        repository.saveBestScore(boardSize, timeInSeconds)

        verify(exactly = 0) { preferencesManager.putLong(any(), any()) }
    }

    @Test
    fun `saveBestScore handles different board sizes independently`() {
        val boardSize4 = 4
        val boardSize8 = 8
        val time4x4 = 60L
        val time8x8 = 120L
        val key4 = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize4"
        val key8 = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize8"

        every { preferencesManager.getLong(key4, -1L) } returns -1L
        every { preferencesManager.getLong(key8, -1L) } returns -1L
        every { preferencesManager.putLong(any(), any()) } just Runs

        repository.saveBestScore(boardSize4, time4x4)
        repository.saveBestScore(boardSize8, time8x8)

        verify { preferencesManager.putLong(key4, time4x4) }
        verify { preferencesManager.putLong(key8, time8x8) }
    }

    @Test
    fun `getAllBestScores returns empty list when no scores exist`() = runTest {
        every { preferencesManager.getAllKeys() } returns emptySet()

        val result = repository.getAllBestScores()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAllBestScores returns single score when one exists`() = runTest {
        val boardSize = 8
        val timeInSeconds = 120L
        val key = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize"
        val formattedTime = "02:00"

        every { preferencesManager.getAllKeys() } returns setOf(key)
        every { preferencesManager.getLong(key, -1L) } returns timeInSeconds
        every { timeFormatter.formatTime(timeInSeconds) } returns formattedTime

        val result = repository.getAllBestScores()

        assertEquals(1, result.size)
        assertEquals(boardSize, result[0].boardSize)
        assertEquals(formattedTime, result[0].time)
    }

    @Test
    fun `getAllBestScores returns multiple scores when they exist`() = runTest {
        val boardSize4 = 4
        val boardSize8 = 8
        val time4x4 = 60L
        val time8x8 = 120L
        val key4 = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize4"
        val key8 = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize8"
        val formattedTime4 = "01:00"
        val formattedTime8 = "02:00"

        every { preferencesManager.getAllKeys() } returns setOf(key4, key8)
        every { preferencesManager.getLong(key4, -1L) } returns time4x4
        every { preferencesManager.getLong(key8, -1L) } returns time8x8
        every { timeFormatter.formatTime(time4x4) } returns formattedTime4
        every { timeFormatter.formatTime(time8x8) } returns formattedTime8

        val result = repository.getAllBestScores()

        assertEquals(2, result.size)
        assertTrue(result.any { it.boardSize == boardSize4 && it.time == formattedTime4 })
        assertTrue(result.any { it.boardSize == boardSize8 && it.time == formattedTime8 })
    }

    @Test
    fun `getAllBestScores filters out non-best-score keys`() = runTest {
        val boardSize = 8
        val timeInSeconds = 120L
        val bestScoreKey = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize"
        val otherKey = "some_other_preference_key"
        val formattedTime = "02:00"

        every { preferencesManager.getAllKeys() } returns setOf(bestScoreKey, otherKey)
        every { preferencesManager.getLong(bestScoreKey, -1L) } returns timeInSeconds
        every { timeFormatter.formatTime(timeInSeconds) } returns formattedTime

        val result = repository.getAllBestScores()

        assertEquals(1, result.size)
        assertEquals(boardSize, result[0].boardSize)
    }

    @Test
    fun `getAllBestScores handles invalid board size in key gracefully`() = runTest {
        val validKey = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}8"
        val invalidKey = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}invalid"
        val formattedTime = "02:00"

        every { preferencesManager.getAllKeys() } returns setOf(validKey, invalidKey)
        every { preferencesManager.getLong(validKey, -1L) } returns 120L
        every { preferencesManager.getLong(invalidKey, -1L) } returns 60L
        every { timeFormatter.formatTime(120L) } returns formattedTime

        val result = repository.getAllBestScores()

        assertEquals(1, result.size)
        assertEquals(8, result[0].boardSize)
    }

    @Test
    fun `clearAllBestScores removes all best score keys`() = runTest {
        val key4 = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}4"
        val key8 = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}8"
        val key12 = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}12"

        every { preferencesManager.getAllKeys() } returns setOf(key4, key8, key12)
        every { preferencesManager.remove(any()) } just Runs

        repository.clearAllBestScores()

        verify { preferencesManager.remove(key4) }
        verify { preferencesManager.remove(key8) }
        verify { preferencesManager.remove(key12) }
    }

    @Test
    fun `clearAllBestScores removes only best score keys, not other preferences`() = runTest {
        val bestScoreKey = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}8"
        val otherKey = "some_other_setting"

        every { preferencesManager.getAllKeys() } returns setOf(bestScoreKey, otherKey)
        every { preferencesManager.remove(any()) } just Runs

        repository.clearAllBestScores()

        verify { preferencesManager.remove(bestScoreKey) }
        verify(exactly = 0) { preferencesManager.remove(otherKey) }
    }

    @Test
    fun `clearAllBestScores handles empty preferences gracefully`() = runTest {
        every { preferencesManager.getAllKeys() } returns emptySet()

        repository.clearAllBestScores()

        verify(exactly = 0) { preferencesManager.remove(any()) }
    }

    @Test
    fun `saveBestScore uses correct key format`() {
        val boardSize = 10
        val timeInSeconds = 90L
        val expectedKey = "${PreferencesKeys.KEY_BEST_SCORE_PREFIX}$boardSize"

        every { preferencesManager.getLong(expectedKey, -1L) } returns -1L
        every { preferencesManager.putLong(expectedKey, timeInSeconds) } just Runs

        repository.saveBestScore(boardSize, timeInSeconds)

        verify { preferencesManager.getLong(expectedKey, -1L) }
        verify { preferencesManager.putLong(expectedKey, timeInSeconds) }
    }
}

