package com.brazole.peacefulqueens.game.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameEngineTest {

    private lateinit var gameEngine: GameEngine
    private lateinit var conflictDetector: ConflictDetector

    @Before
    fun setup() {
        conflictDetector = ConflictDetector()
        gameEngine = GameEngine(conflictDetector)
    }

    @Test
    fun `initializeBoard creates correct number of cells for 4x4 board`() {
        val cells = gameEngine.initializeBoard(4)

        assertEquals(16, cells.size)
    }

    @Test
    fun `initializeBoard creates correct number of cells for 8x8 board`() {
        val cells = gameEngine.initializeBoard(8)

        assertEquals(64, cells.size)
    }

    @Test
    fun `initializeBoard creates correct number of cells for 12x12 board`() {
        val cells = gameEngine.initializeBoard(12)

        assertEquals(144, cells.size)
    }

    @Test
    fun `initializeBoard creates all cells with no queens initially`() {
        val cells = gameEngine.initializeBoard(8)

        assertTrue(cells.all { !it.hasQueen })
    }

    @Test
    fun `initializeBoard creates all cells with no attacks initially`() {
        val cells = gameEngine.initializeBoard(8)

        assertTrue(cells.all { !it.attacked })
    }

    @Test
    fun `initializeBoard creates all cells with no conflicts initially`() {
        val cells = gameEngine.initializeBoard(8)

        assertTrue(cells.all { !it.conflict })
    }

    @Test
    fun `initializeBoard creates cells with correct row and column values`() {
        val cells = gameEngine.initializeBoard(4)

        assertEquals(0, cells[0].row)
        assertEquals(0, cells[0].column)
        assertEquals(0, cells[1].row)
        assertEquals(1, cells[1].column)
        assertEquals(3, cells[15].row)
        assertEquals(3, cells[15].column)
    }

    @Test
    fun `onCellClick places queen on empty cell`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]

        val result = gameEngine.onCellClick(initialCells, cellToClick)

        assertTrue(result[0].hasQueen)
    }

    @Test
    fun `onCellClick removes queen from cell with queen`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]
        val cellsWithQueen = gameEngine.onCellClick(initialCells, cellToClick)

        val result = gameEngine.onCellClick(cellsWithQueen, cellToClick)

        assertFalse(result[0].hasQueen)
    }

    @Test
    fun `onCellClick marks cells in same row as attacked`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]

        val result = gameEngine.onCellClick(initialCells, cellToClick)

        assertTrue(result[1].attacked)
        assertTrue(result[2].attacked)
        assertTrue(result[3].attacked)
    }

    @Test
    fun `onCellClick marks cells in same column as attacked`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]

        val result = gameEngine.onCellClick(initialCells, cellToClick)

        assertTrue(result[4].attacked)
        assertTrue(result[8].attacked)
        assertTrue(result[12].attacked)
    }

    @Test
    fun `onCellClick marks cells on diagonal as attacked`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]

        val result = gameEngine.onCellClick(initialCells, cellToClick)

        assertTrue(result[5].attacked)
        assertTrue(result[10].attacked)
        assertTrue(result[15].attacked)
    }

    @Test
    fun `onCellClick does not mark queen cell as attacked`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]

        val result = gameEngine.onCellClick(initialCells, cellToClick)

        assertFalse(result[0].attacked)
    }

    @Test
    fun `onCellClick detects conflict when two queens on same row`() {
        val initialCells = gameEngine.initializeBoard(4)
        val firstQueen = initialCells[0]
        val secondQueen = initialCells[1]

        val cellsAfterFirst = gameEngine.onCellClick(initialCells, firstQueen)
        val result = gameEngine.onCellClick(cellsAfterFirst, secondQueen)

        assertTrue(result[0].conflict)
        assertTrue(result[1].conflict)
    }

    @Test
    fun `onCellClick detects conflict when two queens on same column`() {
        val initialCells = gameEngine.initializeBoard(4)
        val firstQueen = initialCells[0]
        val secondQueen = initialCells[4]

        val cellsAfterFirst = gameEngine.onCellClick(initialCells, firstQueen)
        val result = gameEngine.onCellClick(cellsAfterFirst, secondQueen)

        assertTrue(result[0].conflict)
        assertTrue(result[4].conflict)
    }

    @Test
    fun `onCellClick detects conflict when two queens on diagonal`() {
        val initialCells = gameEngine.initializeBoard(4)
        val firstQueen = initialCells[0]
        val secondQueen = initialCells[5]

        val cellsAfterFirst = gameEngine.onCellClick(initialCells, firstQueen)
        val result = gameEngine.onCellClick(cellsAfterFirst, secondQueen)

        assertTrue(result[0].conflict)
        assertTrue(result[5].conflict)
    }

    @Test
    fun `onCellClick clears conflict when conflicting queen is removed`() {
        val initialCells = gameEngine.initializeBoard(4)
        val firstQueen = initialCells[0]
        val secondQueen = initialCells[1]

        val cellsAfterFirst = gameEngine.onCellClick(initialCells, firstQueen)
        val cellsWithConflict = gameEngine.onCellClick(cellsAfterFirst, secondQueen)
        val result = gameEngine.onCellClick(cellsWithConflict, secondQueen)

        assertFalse(result[0].conflict)
        assertFalse(result[1].conflict)
    }

    @Test
    fun `onCellClick handles multiple queens with complex attack patterns`() {
        val initialCells = gameEngine.initializeBoard(8)

        var cells = gameEngine.onCellClick(initialCells, initialCells[0])
        cells = gameEngine.onCellClick(cells, cells[18])
        cells = gameEngine.onCellClick(cells, cells[36])

        val queensCount = cells.count { it.hasQueen }
        assertEquals(3, queensCount)
        assertTrue(cells[0].hasQueen)
        assertTrue(cells[18].hasQueen)
        assertTrue(cells[36].hasQueen)
    }

    @Test
    fun `isGameWon returns false when board is empty`() {
        val cells = gameEngine.initializeBoard(4)

        val result = gameEngine.isGameWon(cells)

        assertFalse(result)
    }

    @Test
    fun `isGameWon returns false when not all queens are placed`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cells = gameEngine.onCellClick(initialCells, initialCells[0])

        val result = gameEngine.isGameWon(cells)

        assertFalse(result)
    }

    @Test
    fun `isGameWon returns false when all queens are placed but have conflicts`() {
        val initialCells = gameEngine.initializeBoard(4)
        var cells = gameEngine.onCellClick(initialCells, initialCells[0])
        cells = gameEngine.onCellClick(cells, cells[1])
        cells = gameEngine.onCellClick(cells, cells[2])
        cells = gameEngine.onCellClick(cells, cells[3])

        val result = gameEngine.isGameWon(cells)

        assertFalse(result)
    }

    @Test
    fun `isGameWon returns true when valid 4x4 solution is found`() {
        val initialCells = gameEngine.initializeBoard(4)
        var cells = gameEngine.onCellClick(initialCells, initialCells[1])
        cells = gameEngine.onCellClick(cells, cells[7])
        cells = gameEngine.onCellClick(cells, cells[8])
        cells = gameEngine.onCellClick(cells, cells[14])

        val result = gameEngine.isGameWon(cells)

        assertTrue(result)
    }

    @Test
    fun `isGameWon returns true when valid 8x8 solution is found`() {
        val initialCells = gameEngine.initializeBoard(8)
        var cells = gameEngine.onCellClick(initialCells, initialCells[0])
        cells = gameEngine.onCellClick(cells, cells[12])
        cells = gameEngine.onCellClick(cells, cells[23])
        cells = gameEngine.onCellClick(cells, cells[29])
        cells = gameEngine.onCellClick(cells, cells[34])
        cells = gameEngine.onCellClick(cells, cells[46])
        cells = gameEngine.onCellClick(cells, cells[49])
        cells = gameEngine.onCellClick(cells, cells[59])

        val result = gameEngine.isGameWon(cells)

        assertTrue(result)
    }

    @Test
    fun `isGameWon correctly calculates board size from cell count`() {
        val cells4x4 = gameEngine.initializeBoard(4)
        val cells8x8 = gameEngine.initializeBoard(8)
        val cells12x12 = gameEngine.initializeBoard(12)

        assertFalse(gameEngine.isGameWon(cells4x4))
        assertFalse(gameEngine.isGameWon(cells8x8))
        assertFalse(gameEngine.isGameWon(cells12x12))
    }

    @Test
    fun `onCellClick returns immutable list`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]

        val result = gameEngine.onCellClick(initialCells, cellToClick)

        assertTrue(result is kotlinx.collections.immutable.ImmutableList)
    }

    @Test
    fun `onCellClick does not modify original cell list`() {
        val initialCells = gameEngine.initializeBoard(4)
        val cellToClick = initialCells[0]
        val originalFirstCell = initialCells[0]

        gameEngine.onCellClick(initialCells, cellToClick)

        assertEquals(originalFirstCell, initialCells[0])
        assertFalse(initialCells[0].hasQueen)
    }

    @Test
    fun `complex scenario - place, remove, and replace queens`() {
        val initialCells = gameEngine.initializeBoard(4)

        var cells = gameEngine.onCellClick(initialCells, initialCells[0])
        assertTrue(cells[0].hasQueen)

        cells = gameEngine.onCellClick(cells, cells[0])
        assertFalse(cells[0].hasQueen)

        cells = gameEngine.onCellClick(cells, cells[1])
        assertTrue(cells[1].hasQueen)

        cells = gameEngine.onCellClick(cells, cells[0])
        assertTrue(cells[0].hasQueen)
        assertTrue(cells[1].hasQueen)
        assertTrue(cells[0].conflict)
        assertTrue(cells[1].conflict)
    }

    @Test
    fun `attack calculation updates when queen configuration changes`() {
        val initialCells = gameEngine.initializeBoard(4)

        var cells = gameEngine.onCellClick(initialCells, initialCells[0])
        assertTrue(cells[1].attacked)
        assertTrue(cells[2].attacked)

        cells = gameEngine.onCellClick(cells, cells[0])
        assertFalse(cells[1].attacked)
        assertFalse(cells[2].attacked)

        cells = gameEngine.onCellClick(cells, cells[5])
        assertTrue(cells[1].attacked)
        assertTrue(cells[4].attacked)
    }
}

