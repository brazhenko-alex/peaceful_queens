package com.brazole.peacefulqueens.game.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_C
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.composables.DialogQueens
import com.brazole.peacefulqueens.base.ui.composables.SpacerInBlock
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.game.data.GameCallbacks
import com.brazole.peacefulqueens.game.data.GameUiState
import com.brazole.peacefulqueens.game.data.MenuDialogCallbacks
import com.brazole.peacefulqueens.game.ui.components.ChessBoard
import com.brazole.peacefulqueens.game.ui.components.ControlRow
import com.brazole.peacefulqueens.game.ui.components.GameHeader
import com.brazole.peacefulqueens.game.ui.components.InfoRow
import com.brazole.peacefulqueens.game.ui.components.createPreviewCells
import com.brazole.peacefulqueens.game.ui.dialogs.MenuDialog

@Composable
fun GameCompose(
    uiState: GameUiState,
    callbacks: GameCallbacks
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .displayCutoutPadding()
            .navigationBarsPadding()
            .background(AppTheme.color.background)
    ) {
        val isLandscape = maxWidth > maxHeight

        if (isLandscape) {
            GameComposeLandscape(
                uiState = uiState,
                callbacks = callbacks,
            )
        } else {
            GameComposePortrait(
                uiState = uiState,
                callbacks = callbacks,
            )
        }
    }

    if (uiState.showGameWonDialog) {
        DialogQueens(
            title = stringResource(R.string.win_title),
            message = stringResource(
                R.string.win_message,
                uiState.boardSize,
                uiState.elapsedTimeFormatted
            ),
            textPositive = stringResource(R.string.win_play_again),
            textNegative = stringResource(R.string.win_new_size),
            dismissable = false,
            onConfirm = {
                callbacks.onWinDismiss()
                callbacks.onResetClick()
            },
            onDismiss = {
                callbacks.onWinDismiss()
            }
        )
    }

    if (uiState.showMenuDialog) {
        MenuDialog(
            currentBoardSize = uiState.boardSize,
            showNewGameConfirmDialog = uiState.showNewGameConfirmDialog,
            callbacks = callbacks.menuDialogCallbacks
        )
    }

    if (uiState.showResetConfirmDialog) {
        DialogQueens(
            title = stringResource(R.string.reset_confirmation_title),
            message = stringResource(R.string.reset_confirmation_message),
            textPositive = stringResource(R.string.reset_confirmation_confirm),
            textNegative = stringResource(R.string.reset_confirmation_cancel),
            dismissable = true,
            onConfirm = {
                callbacks.onResetConfirm()
                callbacks.onDismissResetConfirmDialog()
            },
            onDismiss = {
                callbacks.onDismissResetConfirmDialog()
            }
        )
    }
}

@Composable
private fun GameComposePortrait(
    uiState: GameUiState,
    callbacks: GameCallbacks,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        GameHeaderSection(
            uiState = uiState,
            callbacks = callbacks,
            isLandscape = false
        )

        SpacerInBlock()

        ChessBoard(
            onCellClick = callbacks.onCellClick,
            cellData = uiState.cellList,
            hintMode = uiState.hintMode
        )

        SpacerInBlock()

        ControlRow(
            onResetClick = callbacks.onShowResetConfirmDialog,
            onHintClick = callbacks.onHintClick,
            hintMode = uiState.hintMode
        )
    }
}

@Composable
private fun GameComposeLandscape(
    uiState: GameUiState,
    callbacks: GameCallbacks,
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(.6f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            GameHeaderSection(
                uiState = uiState,
                callbacks = callbacks,
                isLandscape = true
            )
            SpacerInBlock()
            ControlRow(
                onResetClick = callbacks.onShowResetConfirmDialog,
                onHintClick = callbacks.onHintClick,
                hintMode = uiState.hintMode
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            ChessBoard(
                onCellClick = callbacks.onCellClick,
                cellData = uiState.cellList,
                hintMode = uiState.hintMode
            )
        }
    }
}

@Composable
private fun GameHeaderSection(
    uiState: GameUiState,
    callbacks: GameCallbacks,
    isLandscape: Boolean
) {
    GameHeader(
        onSettingsClick = callbacks.onSettingsClick,
        onBestScoreClick = callbacks.onViewBestScores
    )

    SpacerInBlock()

    InfoRow(
        queensRemaining = uiState.queensRemaining,
        elapsedTimeFormatted = uiState.elapsedTimeFormatted,
        isLandscape = isLandscape
    )
}

private val dummyCallbacks = GameCallbacks(
    onCellClick = {},
    onResetClick = {},
    onHintClick = {},
    onWinDismiss = {},
    onViewBestScores = {},
    onSettingsClick = {},
    menuDialogCallbacks = MenuDialogCallbacks(
        onDismiss = {},
        onConfirmDialogConfirm = {},
        onShowNewGameConfirmDialog = {},
        onConfirmDialogDismiss = {}
    ),
    onShowResetConfirmDialog = {},
    onResetConfirm = {},
    onDismissResetConfirmDialog = {}
)

@Preview(name = "Portrait - Empty Board 8x8", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposePortraitEmpty() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 8,
                elapsedTimeFormatted = "00:00",
                cellList = createPreviewCells(boardSize = 8)
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Portrait - In Progress", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposePortraitInProgress() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 5,
                elapsedTimeFormatted = "03:45",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 0, 1 to 2, 2 to 4),
                    showAttacked = true
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Portrait - With Conflicts", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposePortraitConflicts() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 6,
                elapsedTimeFormatted = "05:20",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 0, 1 to 1),
                    conflictPositions = listOf(0 to 0, 1 to 1)
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Portrait - Hint Mode", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposePortraitHintMode() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 6,
                elapsedTimeFormatted = "02:15",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 1, 1 to 3),
                    showAttacked = true
                ),
                hintMode = true
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Portrait - Small Board 4x4", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposePortraitSmallBoard() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 4,
                queensRemaining = 4,
                elapsedTimeFormatted = "00:30",
                cellList = createPreviewCells(boardSize = 4)
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Portrait - Large Board 12x12", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposePortraitLargeBoard() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 12,
                queensRemaining = 12,
                elapsedTimeFormatted = "00:00",
                cellList = createPreviewCells(boardSize = 12)
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Portrait - Long Time", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposePortraitLongTime() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 3,
                elapsedTimeFormatted = "99:59",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 1, 1 to 3, 2 to 5, 3 to 0, 4 to 2),
                    showAttacked = true
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Landscape - Empty Board", showBackground = true, device = PIXEL_C)
@Composable
private fun PreviewGameComposeLandscapeEmpty() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 8,
                elapsedTimeFormatted = "00:00",
                cellList = createPreviewCells(boardSize = 8)
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Landscape - In Progress", showBackground = true, device = PIXEL_C)
@Composable
private fun PreviewGameComposeLandscapeInProgress() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 4,
                elapsedTimeFormatted = "07:30",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 0, 1 to 4, 2 to 7, 3 to 5),
                    showAttacked = true
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Landscape - Hint Mode", showBackground = true, device = PIXEL_C)
@Composable
private fun PreviewGameComposeLandscapeHintMode() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 5,
                elapsedTimeFormatted = "04:20",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 1, 1 to 3, 2 to 5),
                    showAttacked = true
                ),
                hintMode = true
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Tablet Portrait", showBackground = true, widthDp = 768, heightDp = 1024)
@Composable
private fun PreviewGameComposeTabletPortrait() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 6,
                elapsedTimeFormatted = "02:45",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 0, 1 to 2)
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Tablet Landscape", showBackground = true, widthDp = 1024, heightDp = 768)
@Composable
private fun PreviewGameComposeTabletLandscape() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 6,
                elapsedTimeFormatted = "02:45",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(0 to 0, 1 to 2)
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Almost Complete", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposeAlmostComplete() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 1,
                elapsedTimeFormatted = "15:42",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(
                        0 to 0, 1 to 4, 2 to 7, 3 to 5,
                        4 to 2, 5 to 6, 6 to 1
                    ),
                    showAttacked = true
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Edge - Minimum Board 1x1", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposeEdgeMinimumBoard() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 1,
                queensRemaining = 1,
                elapsedTimeFormatted = "00:00",
                cellList = createPreviewCells(boardSize = 1)
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Edge - Maximum Queens Placed", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposeEdgeMaxQueens() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 8,
                queensRemaining = 0,
                elapsedTimeFormatted = "25:30",
                cellList = createPreviewCells(
                    boardSize = 8,
                    queensPositions = listOf(
                        0 to 0, 1 to 4, 2 to 7, 3 to 5,
                        4 to 2, 5 to 6, 6 to 1, 7 to 3
                    ),
                    showAttacked = true
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

@Preview(name = "Edge - All Conflicts", showBackground = true, widthDp = 360, heightDp = 800)
@Composable
private fun PreviewGameComposeEdgeAllConflicts() {
    AppTheme {
        GameCompose(
            uiState = GameUiState(
                boardSize = 4,
                queensRemaining = 0,
                elapsedTimeFormatted = "10:00",
                cellList = createPreviewCells(
                    boardSize = 4,
                    queensPositions = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3),
                    conflictPositions = listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3)
                )
            ),
            callbacks = dummyCallbacks
        )
    }
}

