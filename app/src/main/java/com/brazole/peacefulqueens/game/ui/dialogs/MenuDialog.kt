package com.brazole.peacefulqueens.game.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_2
import androidx.compose.ui.tooling.preview.Devices.PIXEL_C
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.composables.AppDialogContainer
import com.brazole.peacefulqueens.base.ui.composables.DialogHeader
import com.brazole.peacefulqueens.base.ui.composables.DialogQueens
import com.brazole.peacefulqueens.base.ui.composables.SpacerInBlock
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens
import com.brazole.peacefulqueens.util.Constants

@Composable
fun MenuDialog(
    currentBoardSize: Int,
    onDismiss: () -> Unit,
    onShowNewGameConfirmDialog: () -> Unit,
    showNewGameConfirmDialog: Boolean,
    onConfirmDialogDismiss: () -> Unit,
    onConfirmDialogConfirm: (Int) -> Unit
) {
    var selectedBoardSize by rememberSaveable { mutableIntStateOf(currentBoardSize) }

    Dialog(onDismissRequest = { onDismiss() }) {
        AppDialogContainer {
            Column(
                modifier = Modifier.padding(
                    horizontal = Dimens.paddingHorizontal,
                    vertical = Dimens.paddingVertical
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DialogHeader(
                    title = stringResource(R.string.settings),
                    onDismiss = onDismiss
                )

                SpacerInBlock()

                Text(
                    text = stringResource(R.string.menu_current_board_size, currentBoardSize),
                    style = AppTheme.typography.textMedium,
                    textAlign = TextAlign.Center
                )

                SpacerInBlock()

                Text(
                    text = stringResource(R.string.menu_new_board_size),
                    style = AppTheme.typography.text,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                BoardSizePicker(
                    selectedSize = selectedBoardSize,
                    onSizeChange = { newSize ->
                        selectedBoardSize = newSize.coerceAtLeast(Constants.BOARD_SIZE_MIN)
                    }
                )

                SpacerInBlock()

                Button(
                    onClick = onShowNewGameConfirmDialog,
                    enabled = selectedBoardSize != currentBoardSize
                ) {
                    Text(
                        text = stringResource(R.string.menu_save_board_size),
                        style = AppTheme.typography.textMedium
                    )
                }
            }
        }
    }

    if (showNewGameConfirmDialog) {
        DialogQueens(
            message = stringResource(R.string.menu_change_board_size_confirmation),
            onConfirm = {
                onConfirmDialogConfirm(selectedBoardSize)
            },
            onDismiss = onConfirmDialogDismiss
        )
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun MenuDialogPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            MenuDialog(
                currentBoardSize = 8,
                showNewGameConfirmDialog = false,
                onDismiss = {},
                onConfirmDialogConfirm = {},
                onShowNewGameConfirmDialog = {},
                onConfirmDialogDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun MenuDialogPreviewSmallBoard() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            MenuDialog(
                currentBoardSize = 4,
                showNewGameConfirmDialog = false,
                onDismiss = {},
                onConfirmDialogConfirm = {},
                onShowNewGameConfirmDialog = {},
                onConfirmDialogDismiss = {}
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun MenuDialogPreviewLargeBoard() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            MenuDialog(
                currentBoardSize = 15,
                showNewGameConfirmDialog = false,
                onDismiss = {},
                onConfirmDialogConfirm = {},
                onShowNewGameConfirmDialog = {},
                onConfirmDialogDismiss = {}
            )
        }
    }
}
