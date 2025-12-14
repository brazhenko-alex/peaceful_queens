package com.brazole.peacefulqueens.game.domain

import com.brazole.peacefulqueens.game.data.Cell
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

class GameEngine @Inject constructor(private val conflictDetector: ConflictDetector) {

    fun onCellClick(currentCells: ImmutableList<Cell>, clickedCell: Cell): ImmutableList<Cell> {
        val updatedCells = currentCells.map { cell ->
            if (cell.row == clickedCell.row && cell.column == clickedCell.column) {
                cell.copy(hasQueen = !cell.hasQueen)
            } else {
                cell
            }
        }.toImmutableList()

        return recalculateAttacksAndConflicts(updatedCells)
    }

    private fun recalculateAttacksAndConflicts(cells: ImmutableList<Cell>): ImmutableList<Cell> {
        val queens = cells.filter { it.hasQueen }

        return cells.map { cell ->
            var attacked = false
            var conflict = false
            for (queen in queens) {
                if (queen.row == cell.row && queen.column == cell.column) {
                    continue
                }

                if (conflictDetector.isAttacking(
                        queen.row, queen.column,
                        cell.row, cell.column,
                    )) {
                    attacked = true
                    if (!cell.hasQueen) {
                        break
                    }

                    conflict = true
                    break
                }
            }
            cell.copy(
                attacked = attacked,
                conflict = conflict
            )
        }.toImmutableList()
    }

    fun isGameWon(cells: ImmutableList<Cell>): Boolean {
        val queensCount = cells.count { it.hasQueen }
        val boardSize = kotlin.math.sqrt(cells.size.toDouble()).toInt()
        if (queensCount != boardSize) {
            return false
        }

        return cells.none { it.conflict }
    }

    fun initializeBoard(boardSize: Int): ImmutableList<Cell> {
        return buildList {
            for (row in 0 until boardSize) {
                for (column in 0 until boardSize) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = false,
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()
    }
}

