package com.brazole.peacefulqueens.game.ui.components

import android.graphics.Matrix
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.composables.SpacerInBlock
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens

@Composable
fun InfoRow(
    elapsedTimeFormatted: String,
    queensRemaining: Int,
    isLandscape: Boolean = true
) {
    val rotationAngle = 73f
    val containerColor = AppTheme.color.backgroundDark
    val glowColorStart = Color.White.copy(alpha = .4f)
    val glowColorEnd = Color.Black.copy(alpha = .5f)

    val glowRadius = 12.dp
    val glowRadiusPx = with(LocalDensity.current) { glowRadius.toPx() }
    val cornerRadiusPx = with(LocalDensity.current) { Dimens.cornerRadius.toPx() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val paint = android.graphics.Paint().apply {
                    isAntiAlias = true

                    shader = android.graphics.LinearGradient(
                        0f, 0f,
                        size.width, size.height,
                        intArrayOf(
                            glowColorStart.toArgb(),
                            glowColorStart.toArgb(),
                            glowColorEnd.toArgb(),
                            glowColorEnd.toArgb()
                        ),
                        floatArrayOf(0f, 0.4f, 0.5f, 1f), // sharp split in the middle
                        android.graphics.Shader.TileMode.CLAMP
                    )

                    val matrix = Matrix()
                    matrix.setRotate(rotationAngle, size.width / 2f, size.height / 2f)
                    shader.setLocalMatrix(matrix)

                    this.shader = shader

                    maskFilter = android.graphics.BlurMaskFilter(
                        glowRadiusPx,
                        android.graphics.BlurMaskFilter.Blur.NORMAL
                    )
                }

                drawIntoCanvas { canvas ->
                    canvas.nativeCanvas.drawRoundRect(
                        android.graphics.RectF(0f, 0f, size.width, size.height),
                        cornerRadiusPx,
                        cornerRadiusPx,
                        paint
                    )
                }
            }
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(containerColor)
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            )
    ) {
        if (!isLandscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoContent(
                    elapsedTimeFormatted = elapsedTimeFormatted,
                    queensRemaining = queensRemaining,
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfoContent(
                    elapsedTimeFormatted = elapsedTimeFormatted,
                    queensRemaining = queensRemaining
                )
            }
        }
    }
}

@Composable
private fun InfoContent(
    elapsedTimeFormatted: String,
    queensRemaining: Int,
) {
    Text(
        text = stringResource(R.string.queens_left, queensRemaining),
        style = AppTheme.typography.textMedium
    )

    SpacerInBlock()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_schedule),
            contentDescription = stringResource(R.string.elapsed_time),
            tint = AppTheme.color.iconTint,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = elapsedTimeFormatted,
            style = AppTheme.typography.textMedium
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true)
@Composable
private fun PreviewInfoRowEmpty() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(color = AppTheme.color.background)
                .padding(24.dp)

        ) {
            InfoRow(
                elapsedTimeFormatted = "2:23",
                queensRemaining = 3
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true)
@Composable
private fun PreviewInfoRowEmptyLandScape() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(color = AppTheme.color.background)
                .padding(24.dp)

        ) {
            InfoRow(
                elapsedTimeFormatted = "2:23",
                queensRemaining = 3,
                isLandscape = false
            )
        }
    }
}
@Preview(showBackground = true, widthDp = 300)
@Composable
private fun PreviewInfoRowEmptyLandSc8ape() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(color = AppTheme.color.background)
                .padding(24.dp)

        ) {
            InfoRow(
                elapsedTimeFormatted = "2:23",
                queensRemaining = 3,
                isLandscape = true
            )
        }
    }
}
