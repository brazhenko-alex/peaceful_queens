package com.brazole.peacefulqueens.game.ui.dialogs

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.util.Constants

@Composable
fun BoardSizePicker(
    selectedSize: Int,
    onSizeChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = { onSizeChange(selectedSize - 1) },
            enabled = selectedSize > Constants.BOARD_SIZE_MIN
        ) {
            Text(
                text = "-",
                style = AppTheme.typography.title
            )
        }

        Text(
            modifier = Modifier.width(140.dp),
            text = "$selectedSize",
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = AppTheme.typography.title,
        )

        Button(onClick = { onSizeChange(selectedSize + 1) }) {
            Text(
                text = "+",
                style = AppTheme.typography.title
            )
        }
    }
}

@Preview(name = "Minimum Size")
@Composable
private fun BoardSizePickerMinimumPreview() {
    AppTheme {

        BoardSizePicker(
            selectedSize = Constants.BOARD_SIZE_MIN,
            onSizeChange = {}
        )
    }
}

@Preview(name = "Small Size")
@Composable
private fun BoardSizePickerSmallPreview() {
    AppTheme {
        BoardSizePicker(
            selectedSize = 6,
            onSizeChange = {}
        )
    }
}

@Preview(name = "Medium Size")
@Composable
private fun BoardSizePickerMediumPreview() {
    AppTheme {
        BoardSizePicker(
            selectedSize = 18,
            onSizeChange = {}
        )
    }
}

@Preview(name = "Large Size")
@Composable
private fun BoardSizePickerLargePreview() {
    AppTheme {
        BoardSizePicker(
            selectedSize = 200,
            onSizeChange = {}
        )
    }
}

@Preview(name = "Extra Large Size")
@Composable
private fun BoardSizePickerExtraLargePreview() {
    AppTheme {
        BoardSizePicker(
            selectedSize = 12000,
            onSizeChange = {}
        )
    }
}

