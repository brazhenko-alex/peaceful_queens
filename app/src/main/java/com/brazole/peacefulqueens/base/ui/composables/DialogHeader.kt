package com.brazole.peacefulqueens.base.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.bestScores.ui.CloseShadowButton

@Composable
fun DialogHeader(
    title: String,
    onDismiss: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            style = AppTheme.typography.title
        )

        CloseShadowButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = onDismiss
        )
    }
}