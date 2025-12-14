package com.brazole.peacefulqueens.base.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens

@Composable
fun DialogHeader(
    modifier: Modifier = Modifier,
    title: String,
    onDismiss: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        val buttonSize = Dimens.iconButtonSize
        Spacer(Modifier.width(buttonSize))
        Text(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp),
            text = title,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.title
        )

        NeonGlowIcon(
            painter = painterResource(R.drawable.ic_close),
            contentDescription = stringResource(R.string.close_dialog),
            onClick = onDismiss,
            glowColor = AppTheme.color.iconTint,
            buttonSize = buttonSize
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DialogHeaderPreview32() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            DialogHeader(
                modifier = Modifier.background(color = AppTheme.color.background),
                title = stringResource(R.string.best_scores),
                onDismiss = {}
            )
            DialogHeader(
                title = "",
                onDismiss = {}
            )
            DialogHeader(
                title = "Lorem ipsum dolor sit amet, con s e ct e t ur adipiscing elit. Sed do eiusmod tempor incididunt ut labore" +
                        " et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut" +
                        " aliquip ex ea commodo consequat.",
                onDismiss = {}
            )
        }
    }
}