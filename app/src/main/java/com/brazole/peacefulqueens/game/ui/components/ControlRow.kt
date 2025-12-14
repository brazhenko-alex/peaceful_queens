package com.brazole.peacefulqueens.game.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.composables.NeonGlowIcon
import com.brazole.peacefulqueens.base.ui.theme.AppTheme

@Composable
fun ControlRow(
    onResetClick: () -> Unit,
    onHintClick: () -> Unit,
    hintMode: Boolean = false,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_restart),
                contentDescription = stringResource(R.string.game_reset),
                onClick = onResetClick,
            )

            val hintColor = if (hintMode) {
                AppTheme.color.queenMagenta
            } else {
                AppTheme.color.iconTint
            }

            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_hint),
                contentDescription = stringResource(R.string.hint),
                onClick = onHintClick,
                glowColor = hintColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGameHeaderEmpty() {
    AppTheme {
        ControlRow(
            onResetClick = {},
            onHintClick = {},
            hintMode = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGameHeaderEmpty2() {
    AppTheme {
        ControlRow(
            onResetClick = {},
            onHintClick = {},
            hintMode = false
        )
    }
}
