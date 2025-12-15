package com.brazole.peacefulqueens.game.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices.PIXEL_2
import androidx.compose.ui.tooling.preview.Devices.PIXEL_C
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.composables.AppDialogContainer
import com.brazole.peacefulqueens.base.ui.composables.SpacerInBlock
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens

@Composable
fun GameWonDialog(
    boardSize: Int,
    elapsedTimeFormatted: String,
    onPlayAgain: () -> Unit,
    onNewSize: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        AppDialogContainer {
            Column(
                modifier = Modifier.padding(
                    horizontal = Dimens.paddingHorizontal,
                    vertical = Dimens.paddingVertical
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(R.raw.lottie_trophy)
                )

                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.size(120.dp)
                )

                SpacerInBlock()

                Text(
                    text = stringResource(
                        R.string.win_message,
                        boardSize,
                        elapsedTimeFormatted
                    ),
                    style = AppTheme.typography.textMedium,
                    textAlign = TextAlign.Center
                )

                SpacerInBlock()

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(
                        modifier = Modifier.weight(.5f),
                        onClick = onNewSize
                    ) {
                        DialogButtonText(text = stringResource(R.string.win_new_size))
                    }
                    Button(
                        modifier = Modifier.weight(.5f),
                        onClick = onPlayAgain
                    ) {
                        DialogButtonText(text = stringResource(R.string.win_play_again))
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogButtonText(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.textMedium,
        maxLines = 1,
        textAlign = TextAlign.Center,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun GameWonDialogPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            GameWonDialog(
                boardSize = 8,
                elapsedTimeFormatted = "02:35",
                onPlayAgain = {},
                onNewSize = {}
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun GameWonDialogPreviewSmallBoard() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            GameWonDialog(
                boardSize = 4,
                elapsedTimeFormatted = "00:45",
                onPlayAgain = {},
                onNewSize = {}
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun GameWonDialogPreviewLargeBoard() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            GameWonDialog(
                boardSize = 15,
                elapsedTimeFormatted = "25:30",
                onPlayAgain = {},
                onNewSize = {}
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun GameWonDialogPreviewLongTime() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            GameWonDialog(
                boardSize = 12,
                elapsedTimeFormatted = "99:59",
                onPlayAgain = {},
                onNewSize = {}
            )
        }
    }
}

