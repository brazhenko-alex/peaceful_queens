package com.brazole.peacefulqueens.base.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens

@Composable
fun DialogQueens(
    message: String,
    textNegative: String = stringResource(R.string.ok),
    textPositive: String = stringResource(R.string.cancel),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        AppDialogContainer {
            Column(
                modifier = Modifier.padding(
                    horizontal = Dimens.paddingHorizontal,
                    vertical = Dimens.paddingVertical
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = message,
                    style = AppTheme.typography.textMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(
                        modifier = Modifier.weight(.5f),
                        onClick = onDismiss
                    ) {
                        DialogButtonText(text = textPositive)
                    }
                    Button(
                        modifier = Modifier.weight(.5f),
                        onClick = {
                            onConfirm()
                            onDismiss()
                        }
                    ) {
                        DialogButtonText(text = textNegative)
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
private fun DialogQueensPreviewEmpty() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            DialogQueens(
                message = "",
                onConfirm = {},
                onDismiss = {},
                textNegative = "",
                textPositive = ""
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun DialogQueensPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            DialogQueens(
                message = "Some very Important message",
                onConfirm = {},
                onDismiss = {},
                textNegative = "OK",
                textPositive = "Cancel"
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun DialogQueensPreviewLongText() {
    val lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut " +
            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
            " nisi ut aliquip ex ea commodo consequat."
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            DialogQueens(
                message = lorem,
                onConfirm = {},
                onDismiss = {},
                textNegative = lorem,
                textPositive = lorem
            )
        }
    }
}
