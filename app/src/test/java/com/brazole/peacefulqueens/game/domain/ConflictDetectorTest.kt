package com.brazole.peacefulqueens.game.domain

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ConflictDetectorTest {

    private lateinit var conflictDetector: ConflictDetector

    @Before
    fun setup() {
        conflictDetector = ConflictDetector()
    }

    @Test
    fun `isAttacking returns false when same position`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 0,
            targetColumn = 0
        )
        assertFalse(result)
    }

    @Test
    fun `isAttacking returns true when queens on same row, different columns`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 0,
            targetColumn = 5
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens on same row at opposite ends`() {
        val result = conflictDetector.isAttacking(
            queenRow = 3,
            queenColumn = 0,
            targetRow = 3,
            targetColumn = 7
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens on same column, different rows`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 7,
            targetColumn = 0
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens on same column at opposite ends`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 4,
            targetRow = 7,
            targetColumn = 4
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens on main diagonal going down-right`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 4,
            targetColumn = 4
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens on main diagonal going up-left`() {
        val result = conflictDetector.isAttacking(
            queenRow = 5,
            queenColumn = 5,
            targetRow = 1,
            targetColumn = 1
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens on anti-diagonal going up-right`() {
        val result = conflictDetector.isAttacking(
            queenRow = 7,
            queenColumn = 0,
            targetRow = 3,
            targetColumn = 4
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens on anti-diagonal going down-left`() {
        val result = conflictDetector.isAttacking(
            queenRow = 2,
            queenColumn = 6,
            targetRow = 5,
            targetColumn = 3
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns false when queens not aligned on row, column, or diagonal`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 2,
            targetColumn = 3
        )
        assertFalse(result)
    }

    @Test
    fun `isAttacking returns false when queens are knight move apart`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 1,
            targetColumn = 2
        )
        assertFalse(result)
    }

    @Test
    fun `isAttacking returns false when queens are in L shape`() {
        val result = conflictDetector.isAttacking(
            queenRow = 3,
            queenColumn = 3,
            targetRow = 5,
            targetColumn = 4
        )
        assertFalse(result)
    }

    @Test
    fun `isAttacking returns true when queens are adjacent on row`() {
        val result = conflictDetector.isAttacking(
            queenRow = 4,
            queenColumn = 3,
            targetRow = 4,
            targetColumn = 4
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens are adjacent on column`() {
        val result = conflictDetector.isAttacking(
            queenRow = 3,
            queenColumn = 4,
            targetRow = 4,
            targetColumn = 4
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns true when queens are adjacent on diagonal`() {
        val result = conflictDetector.isAttacking(
            queenRow = 3,
            queenColumn = 3,
            targetRow = 4,
            targetColumn = 4
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking works correctly on minimum board 4x4 - same row`() {
        val result = conflictDetector.isAttacking(
            queenRow = 1,
            queenColumn = 0,
            targetRow = 1,
            targetColumn = 3
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking works correctly on minimum board 4x4 - diagonal`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 3,
            targetColumn = 3
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking works correctly on large board 15x15 - same column`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 12,
            targetRow = 14,
            targetColumn = 12
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking works correctly on large board 15x15 - diagonal`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 14,
            targetColumn = 14
        )
        assertTrue(result)
    }

    @Test
    fun `isAttacking returns false for valid queen placement in 8-Queens solution`() {
        val result = conflictDetector.isAttacking(
            queenRow = 0,
            queenColumn = 0,
            targetRow = 1,
            targetColumn = 4
        )
        assertFalse(result)
    }

    @Test
    fun `isAttacking handles symmetric positions - reverse parameters`() {
        val result1 = conflictDetector.isAttacking(
            queenRow = 2,
            queenColumn = 3,
            targetRow = 5,
            targetColumn = 6
        )
        val result2 = conflictDetector.isAttacking(
            queenRow = 5,
            queenColumn = 6,
            targetRow = 2,
            targetColumn = 3
        )
        assertTrue(result1)
        assertTrue(result2)
        assertTrue(result1 == result2)
    }
}

