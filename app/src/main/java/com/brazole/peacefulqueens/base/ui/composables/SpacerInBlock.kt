package com.brazole.peacefulqueens.base.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens

@Composable
fun SpacerInBlock() {
    Spacer(Modifier.size(Dimens.spacerInBlock))
}

@Preview(showBackground = true)
@Composable
private fun SpacerInBlockPreviewColumn() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            PreviewContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SpacerInBlockPreviewRow() {
    AppTheme {
        Row(modifier = Modifier.fillMaxSize()) {
            PreviewContent()
        }
    }
}

@Composable
private fun PreviewContent() {
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(color = AppTheme.color.queenMagenta)
    )
    SpacerInBlock()
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(color = AppTheme.color.queenMagenta)
    )
    SpacerInBlock()
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(color = AppTheme.color.queenMagenta)
    )
    SpacerInBlock()
    Box(
        modifier = Modifier
            .size(64.dp)
            .background(color = AppTheme.color.queenMagenta)
    )
}