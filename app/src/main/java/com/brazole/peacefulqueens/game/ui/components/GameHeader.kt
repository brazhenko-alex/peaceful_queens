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
fun GameHeader(
    onSettingsClick: () -> Unit,
    onBestScoreClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = stringResource(R.string.settings),
                onClick = onSettingsClick,
            )
            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_trophy),
                contentDescription = stringResource(R.string.best_scores),
                onClick = onBestScoreClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGameHeaderEmpty() {
    AppTheme {
        GameHeader(
            onSettingsClick = {},
            onBestScoreClick = {}
        )
    }
}
