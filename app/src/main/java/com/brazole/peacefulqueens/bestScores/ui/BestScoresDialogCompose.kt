package com.brazole.peacefulqueens.bestScores.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_2
import androidx.compose.ui.tooling.preview.Devices.PIXEL_C
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.composables.AppDialogContainer
import com.brazole.peacefulqueens.base.ui.composables.DialogHeader
import com.brazole.peacefulqueens.base.ui.composables.LoadingCompose
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens
import com.brazole.peacefulqueens.bestScores.data.BestScore
import com.brazole.peacefulqueens.bestScores.data.BestScoresCallbacks
import com.brazole.peacefulqueens.bestScores.data.BestScoresUiState
import com.brazole.peacefulqueens.bestScores.ui.dialogs.ClearConfirmDialog
import kotlinx.collections.immutable.toImmutableList

@Composable
fun BestScoresDialog(
    uiState: BestScoresUiState,
    callbacks: BestScoresCallbacks,
) {
    AppDialogContainer {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.scores.isNotEmpty()) {
                ScoreTable(
                    scores = uiState.scores,
                    queensLeft = uiState.queensLeft,
                    onClearAll = callbacks.onClearAll,
                    onDismiss = callbacks.onDismiss
                )
            } else if (uiState.isLoading.not()) {
                EmptyState(
                    queensLeft = uiState.queensLeft,
                    onDismiss = callbacks.onDismiss
                )
            }
        }
        if (uiState.showClearConfirmationDialog) {
            ClearConfirmDialog(
                onConfirm = callbacks.onClearDialogConfirm,
                onDismiss = callbacks.onClearDialogDismiss
            )
        }
        if (uiState.isLoading) {
            LoadingCompose()
        }
    }
}

@Composable
private fun ScoreTable(
    scores: List<BestScore>,
    queensLeft: Int,
    onClearAll: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DialogHeader(
            title = stringResource(R.string.best_scores),
            onDismiss = onDismiss
        )
        SpacerInDialog()

        TableHeader()

        ScoreHorizontalDivider()

        LazyColumn(
            modifier = Modifier.weight(
                weight = 1f,
                fill = false
            )
        ) {
            items(scores) { score ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.paddingVertical),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.board_size_x, score.boardSize, score.boardSize),
                        style = AppTheme.typography.text
                    )
                    Text(
                        text = score.time,
                        style = AppTheme.typography.text
                    )
                }
                ScoreHorizontalDivider()
            }
        }
        SpacerInDialog()

        Text(
            text = pluralStringResource(
                id = R.plurals.place_more_queens_might_appear_here,
                count = queensLeft,
                queensLeft
            ),
            style = AppTheme.typography.text,
            textAlign = TextAlign.Center
        )

        SpacerInDialog()

        NeonGlowButton(
            text = stringResource(R.string.clear_all),
            onClick = onClearAll
        )
    }
}

@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.board_size),
            style = AppTheme.typography.textMedium
        )
        Text(
            text = stringResource(R.string.best_time),
            style = AppTheme.typography.textMedium
        )
    }
}

@Composable
private fun ScoreHorizontalDivider() {
    HorizontalDivider(
        color = AppTheme.color.divider,
        thickness = 1.dp
    )
}

@Composable
private fun SpacerInDialog() {
    Spacer(modifier = Modifier.height(32.dp))
}

@Composable
private fun EmptyState(
    queensLeft: Int,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Icon(
            painter = painterResource(R.drawable.ic_trophy),
            contentDescription = "No Scores",
            tint = AppTheme.color.textPrimary,
            modifier = Modifier.size(64.dp)
        )
        SpacerInDialog()
        Text(
            text = stringResource(R.string.best_scores_empty),
            style = AppTheme.typography.text,
            textAlign = TextAlign.Center
        )

        SpacerInDialog()

        Text(
            text = pluralStringResource(
                id = R.plurals.place_more_queens,
                count = queensLeft,
                queensLeft
            ),
            style = AppTheme.typography.text,
            textAlign = TextAlign.Center
        )

        SpacerInDialog()

        NeonGlowButton(
            text = stringResource(R.string.best_scores_close),
            onClick = onDismiss,
        )
    }
}

private val dummyCallbacks = BestScoresCallbacks(
    {}, {}, {}, {}
)

private val dummyScores = listOf(
    BestScore(boardSize = 4, time = "00:45"),
    BestScore(boardSize = 5, time = "01:30"),
    BestScore(boardSize = 6, time = "02:15"),
    BestScore(boardSize = 7, time = "03:00"),
    BestScore(boardSize = 8, time = "04:20"),
).toImmutableList()

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun DialogPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            BestScoresDialog(
                uiState = BestScoresUiState(scores = dummyScores),
                callbacks = dummyCallbacks
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun DialogPreviewClearDialog() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            BestScoresDialog(
                uiState = BestScoresUiState(
                    scores = dummyScores,
                    showClearConfirmationDialog = true
                ),
                callbacks = dummyCallbacks
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun DialogPreviewLoading() {
    AppTheme {
        Box(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            BestScoresDialog(
                uiState = BestScoresUiState(
                    scores = dummyScores,
                    isLoading = true
                ),
                callbacks = dummyCallbacks
            )
        }
    }
}

@Preview(showBackground = true, device = PIXEL_2)
@Preview(showBackground = true, device = PIXEL_C)
@Composable
private fun DialogEmptyPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.background)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
            BestScoresDialog(
                uiState = BestScoresUiState(scores = emptyList<BestScore>().toImmutableList()),
                callbacks = dummyCallbacks
            )
        }
    }
}