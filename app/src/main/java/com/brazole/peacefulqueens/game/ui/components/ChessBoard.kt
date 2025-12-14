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

@Preview(
    name = "Chess Board - Empty 8x8",
    showBackground = true,
    widthDp = 400,
    heightDp = 400
)
@Composable
private fun PreviewChessBoardEmpty8x8() {
    AppTheme {
        val cellData = buildList {
            for (row in 0 until 8) {
                for (column in 0 until 8) {
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

        ChessBoard(
            cellData = cellData,
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
        val cellData = buildList {
            for (row in 0 until 8) {
                for (column in 0 until 8) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = row == 0 && column == 0,
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val queenPositions = setOf(
            0 to 0,
            1 to 4,
            2 to 7,
            3 to 5,
            4 to 2
        )
        val cellData = buildList {
            for (row in 0 until 8) {
                for (column in 0 until 8) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = queenPositions.contains(row to column),
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val queenPositions = setOf(
            0 to 0,
            0 to 1,
            1 to 1
        )
        val cellData = buildList {
            for (row in 0 until 8) {
                for (column in 0 until 8) {
                    val hasQueen = queenPositions.contains(row to column)
                    val attacked = row == 0 || row == 1 || column == 0 || column == 1
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = hasQueen,
                            attacked = attacked,
                            conflict = hasQueen && attacked
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val queenPositions = setOf(
            0 to 1,
            1 to 3,
            2 to 0,
            3 to 2
        )
        val cellData = buildList {
            for (row in 0 until 4) {
                for (column in 0 until 4) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = queenPositions.contains(row to column),
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val queenPositions = setOf(
            0 to 0,
            1 to 6,
            2 to 9,
            3 to 3,
            4 to 11
        )
        val cellData = buildList {
            for (row in 0 until 12) {
                for (column in 0 until 12) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = queenPositions.contains(row to column),
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val queenPositions = setOf(
            0 to 0,
            1 to 2,
            2 to 4
        )
        val cellData = buildList {
            for (row in 0 until 15) {
                for (column in 0 until 15) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = queenPositions.contains(row to column),
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val queenPositions = setOf(
            0 to 0,
            1 to 4,
            2 to 7,
            3 to 5,
            4 to 2,
            5 to 6,
            6 to 1,
            7 to 3
        )
        val cellData = buildList {
            for (row in 0 until 8) {
                for (column in 0 until 8) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = queenPositions.contains(row to column),
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val queenPositions = setOf(
            0 to 0,
            0 to 1,
            0 to 2,
            1 to 0,
            1 to 1
        )
        val cellData = buildList {
            for (row in 0 until 8) {
                for (column in 0 until 8) {
                    val hasQueen = queenPositions.contains(row to column)
                    val attacked = row <= 1 || column <= 2
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = hasQueen,
                            attacked = attacked,
                            conflict = hasQueen && attacked
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
        val cellData = buildList {
            for (row in 0 until 3) {
                for (column in 0 until 3) {
                    add(
                        Cell(
                            row = row,
                            column = column,
                            hasQueen = row == 0 && column == 0,
                            attacked = false,
                            conflict = false
                        )
                    )
                }
            }
        }.toImmutableList()

        ChessBoard(
            cellData = cellData,
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
            cell = Cell(
                row = 0,
                column = 0,
                hasQueen = false,
                attacked = false,
                conflict = false
            ),
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
            cell = Cell(
                row = 0,
                column = 1,
                hasQueen = false,
                attacked = false,
                conflict = false
            ),
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
            cell = Cell(
                row = 0,
                column = 0,
                hasQueen = true,
                attacked = false,
                conflict = false
            ),
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
            cell = Cell(
                row = 0,
                column = 1,
                hasQueen = true,
                attacked = false,
                conflict = false
            ),
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
            cell = Cell(
                row = 0,
                column = 0,
                hasQueen = true,
                attacked = true,
                conflict = true
            ),
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
            cell = Cell(
                row = 0,
                column = 0,
                hasQueen = false,
                attacked = true,
                conflict = false
            ),
            hintMode = true,
            onClick = {}
        )
    }
}