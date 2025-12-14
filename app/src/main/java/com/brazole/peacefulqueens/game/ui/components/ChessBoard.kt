package com.brazole.peacefulqueens.game.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.game.data.Cell
import com.brazole.peacefulqueens.game.domain.ConflictDetector
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.math.sqrt

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
    cellData: ImmutableList<Cell>,
    hintMode: Boolean = false,
    onCellClick: (Cell) -> Unit
) {
    val boardSize = sqrt(cellData.size.toDouble()).toInt()
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            for (row in 0 until boardSize) {
                Row(modifier = Modifier.weight(1f)) {
                    for (column in 0 until boardSize) {
                        val cell = cellData[row * boardSize + column]
                        BoardCell(
                            modifier = Modifier.weight(1f),
                            cell = cell,
                            hintMode = hintMode,
                            onClick = { onCellClick(cell) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BoardCell(
    modifier: Modifier = Modifier,
    cell: Cell,
    hintMode: Boolean = false,
    onClick: () -> Unit
) {
    cell.run {
        val isLightCell = (row + column) % 2 == 0

        val backgroundColor =
            if (attacked && hintMode) {
                when {
                    isLightCell -> AppTheme.color.cellAttackedLight
                    else -> AppTheme.color.cellAttackedDark
                }
            } else {
                when {
                    isLightCell -> AppTheme.color.cellLight
                    else -> AppTheme.color.cellDark
                }
            }

        Box(
            modifier = modifier
                .aspectRatio(1f)
                .background(backgroundColor)
                .clickable(onClick = onClick)
                .border(1.dp, AppTheme.color.cellBorder)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {

            if (hasQueen) {
                val coreColor = if (conflict) AppTheme.color.queenRed else AppTheme.color.queenMagenta
                val brightColor = if (conflict) AppTheme.color.queenRedGlow else AppTheme.color.queenMagentaGlow

                CrownComposable(
                    brightColor = brightColor,
                    coreColor = coreColor,
                    glowRadius = 8.dp,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

fun createPreviewCells(
    boardSize: Int,
    queensPositions: List<Pair<Int, Int>> = emptyList(),
    conflictPositions: List<Pair<Int, Int>> = emptyList(),
    showAttacked: Boolean = false
): ImmutableList<Cell> {
    val conflictDetector = ConflictDetector()

    val cells = buildList {
        for (row in 0 until boardSize) {
            for (column in 0 until boardSize) {
                add(
                    Cell(
                        row = row,
                        column = column,
                        hasQueen = queensPositions.contains(row to column),
                        attacked = false,
                        conflict = false
                    )
                )
            }
        }
    }

    if (!showAttacked) {
        return cells.map { cell ->
            cell.copy(conflict = conflictPositions.contains(cell.row to cell.column))
        }.toImmutableList()
    }

    val queensCells = cells.filter { it.hasQueen }

    return cells.map { cell ->
        val isAttacked = queensCells.any { queen ->
            conflictDetector.isAttacking(
                queen.row,
                queen.column,
                cell.row,
                cell.column
            )
        }

        val hasConflict = if (cell.hasQueen) {
            queensCells.any { otherQueen ->
                otherQueen != cell && conflictDetector.isAttacking(
                    otherQueen.row,
                    otherQueen.column,
                    cell.row,
                    cell.column
                )
            }
        } else {
            false
        }

        cell.copy(
            attacked = isAttacked,
            conflict = hasConflict
        )
    }.toImmutableList()
}

@Preview(
    name = "Chess Board - Empty 8x8",
    showBackground = true,
    widthDp = 400,
    heightDp = 400
)
@Composable
private fun PreviewChessBoardEmpty8x8() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(boardSize = 8),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - Single Queen",
    showBackground = true,
    widthDp = 400,
    heightDp = 400
)
@Composable
private fun PreviewChessBoardSingleQueen() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 8,
                queensPositions = listOf(0 to 0)
            ),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - Multiple Queens",
    showBackground = true,
    widthDp = 400,
    heightDp = 400
)
@Composable
private fun PreviewChessBoardMultipleQueens() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 8,
                queensPositions = listOf(0 to 0, 1 to 4, 2 to 7, 3 to 5, 4 to 2)
            ),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - With Conflicts",
    showBackground = true,
    widthDp = 400,
    heightDp = 400
)
@Composable
private fun PreviewChessBoardWithConflicts() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 8,
                queensPositions = listOf(0 to 0, 0 to 1, 1 to 1),
                conflictPositions = listOf(0 to 0, 0 to 1, 1 to 1),
                showAttacked = true
            ),
            hintMode = true,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - Small 4x4",
    showBackground = true,
    widthDp = 300,
    heightDp = 300
)
@Composable
private fun PreviewChessBoardSmall4x4() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 4,
                queensPositions = listOf(0 to 1, 1 to 3, 2 to 0, 3 to 2)
            ),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - Large 12x12",
    showBackground = true,
    widthDp = 500,
    heightDp = 500
)
@Composable
private fun PreviewChessBoardLarge12x12() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 12,
                queensPositions = listOf(0 to 0, 1 to 6, 2 to 9, 3 to 3, 4 to 11)
            ),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - Extra Large 15x15",
    showBackground = true,
    widthDp = 500,
    heightDp = 500
)
@Composable
private fun PreviewChessBoardExtraLarge15x15() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 15,
                queensPositions = listOf(0 to 0, 1 to 2, 2 to 4)
            ),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - Full Solution 8x8",
    showBackground = true,
    widthDp = 400,
    heightDp = 400
)
@Composable
private fun PreviewChessBoardFullSolution() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 8,
                queensPositions = listOf(0 to 0, 1 to 4, 2 to 7, 3 to 5, 4 to 2, 5 to 6, 6 to 1, 7 to 3)
            ),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - All Conflicts",
    showBackground = true,
    widthDp = 400,
    heightDp = 400
)
@Composable
private fun PreviewChessBoardAllConflicts() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 8,
                queensPositions = listOf(4 to 4, 0 to 1, 0 to 2, 1 to 0, 3 to 1),
                conflictPositions = listOf(4 to 4, 0 to 1, 0 to 2, 1 to 0, 3 to 1),
                showAttacked = true
            ),
            hintMode = true,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Chess Board - Minimal 3x3",
    showBackground = true,
    widthDp = 250,
    heightDp = 250
)
@Composable
private fun PreviewChessBoardMinimal3x3() {
    AppTheme {
        ChessBoard(
            cellData = createPreviewCells(
                boardSize = 3,
                queensPositions = listOf(0 to 0)
            ),
            hintMode = false,
            onCellClick = {}
        )
    }
}

@Preview(
    name = "Board Cell - Light Empty",
    showBackground = true,
    widthDp = 80,
    heightDp = 80
)
@Composable
private fun PreviewBoardCellLightEmpty() {
    AppTheme {
        BoardCell(
            cell = createPreviewCells(boardSize = 1)[0],
            hintMode = false,
            onClick = {}
        )
    }
}

@Preview(
    name = "Board Cell - Dark Empty",
    showBackground = true,
    widthDp = 80,
    heightDp = 80
)
@Composable
private fun PreviewBoardCellDarkEmpty() {
    AppTheme {
        BoardCell(
            cell = createPreviewCells(boardSize = 2)[1],
            hintMode = false,
            onClick = {}
        )
    }
}

@Preview(
    name = "Board Cell - Light with Queen",
    showBackground = true,
    widthDp = 80,
    heightDp = 80
)
@Composable
private fun PreviewBoardCellLightWithQueen() {
    AppTheme {
        BoardCell(
            cell = createPreviewCells(
                boardSize = 1,
                queensPositions = listOf(0 to 0)
            )[0],
            hintMode = false,
            onClick = {}
        )
    }
}

@Preview(
    name = "Board Cell - Dark with Queen",
    showBackground = true,
    widthDp = 80,
    heightDp = 80
)
@Composable
private fun PreviewBoardCellDarkWithQueen() {
    AppTheme {
        BoardCell(
            cell = createPreviewCells(
                boardSize = 2,
                queensPositions = listOf(0 to 1)
            )[1],
            hintMode = false,
            onClick = {}
        )
    }
}

@Preview(
    name = "Board Cell - Conflict with Queen",
    showBackground = true,
    widthDp = 80,
    heightDp = 80
)
@Composable
private fun PreviewBoardCellConflictWithQueen() {
    AppTheme {
        BoardCell(
            cell = createPreviewCells(
                boardSize = 1,
                queensPositions = listOf(0 to 0),
                conflictPositions = listOf(0 to 0),
                showAttacked = true
            )[0],
            hintMode = true,
            onClick = {}
        )
    }
}

@Preview(
    name = "Board Cell - Conflict Empty",
    showBackground = true,
    widthDp = 80,
    heightDp = 80
)
@Composable
private fun PreviewBoardCellConflictEmpty() {
    AppTheme {
        BoardCell(
            cell = createPreviewCells(
                boardSize = 2,
                queensPositions = listOf(0 to 1),
                showAttacked = true
            )[0],
            hintMode = true,
            onClick = {}
        )
    }
}