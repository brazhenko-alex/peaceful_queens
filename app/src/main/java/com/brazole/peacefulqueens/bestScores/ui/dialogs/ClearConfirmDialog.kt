package com.brazole.peacefulqueens.bestScores.ui.dialogs

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
import androidx.compose.ui.tooling.preview.Devices.PIXEL_2
import androidx.compose.ui.tooling.preview.Devices.PIXEL_C
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.composables.AppDialogContainer
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens

@Composable
fun ClearConfirmDialog(
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
                    text = stringResource(R.string.are_you_sure_you_want_to_clear_best_score_data),
                    style = AppTheme.typography.textMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.cancel))
                    }
                    Button(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        }
                    ) {
                        Text(stringResource(R.string.clear))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun ClearConfirmDialogPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            ClearConfirmDialog(
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}
