package com.brazole.peacefulqueens.base.ui.composables

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.base.ui.theme.AppTheme

@Composable
fun CloseShadowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    containerColor: Color = AppTheme.color.background.copy(alpha = 0.85f),
    shadowColor: Color = AppTheme.color.iconTint,
    shadowRadius: Dp = 4.dp,
    shadowOffsetY: Dp = 3.dp,
    size: Dp = 32.dp,
) {
    val density = LocalDensity.current
    val shadowRadiusPx = with(density) { shadowRadius.toPx() }
    val shadowOffsetYPx = with(density) { shadowOffsetY.toPx() }

    val paint = remember(shadowRadiusPx, shadowOffsetYPx) {
        Paint().apply {
            isAntiAlias = true
            color = android.graphics.Color.TRANSPARENT
            setShadowLayer(
                shadowRadiusPx,
                0f,
                shadowOffsetYPx,
                shadowColor.copy(alpha = 0.35f).toArgb()
            )
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .padding(bottom = shadowOffsetY)
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val r = this.size.minDimension / 2
                    canvas.nativeCanvas.drawCircle(
                        this.size.width / 2,
                        this.size.height / 2,
                        r,
                        paint
                    )
                }
            }
            .clip(CircleShape)
            .background(containerColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size / 4)) {
            val stroke = (size / 20).toPx()
            drawLine(
                color = shadowColor,
                start = Offset(
                    x = 0f,
                    y = 0f
                ),
                end = Offset(
                    x = this.size.width,
                    y = this.size.height
                ),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
            drawLine(
                color = shadowColor,
                start = Offset(
                    x = this.size.width,
                    y = 0f
                ),
                end = Offset(
                    x = 0f,
                    y = this.size.height
                ),
                strokeWidth = stroke,
                cap = StrokeCap.Round
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CloseButtonPreview14() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.color.background)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            CloseShadowButton(
                onClick = {},
                containerColor = AppTheme.color.background.copy(alpha = .85f),
                shadowColor = AppTheme.color.iconTint,
                shadowRadius = 4.dp,
                shadowOffsetY = 0.dp
            )
            Box(modifier = Modifier) {
                CloseShadowButton(
                    onClick = {},
                )
            }
            CloseShadowButton(
                onClick = {},
                shadowColor = AppTheme.color.queenRed,
                shadowRadius = 12.dp,
                shadowOffsetY = 0.dp
            )
            CloseShadowButton(
                onClick = {},
                containerColor = AppTheme.color.background,
                shadowColor = AppTheme.color.textPrimary,
                shadowRadius = 40.dp,
                shadowOffsetY = 0.dp
            )
            CloseShadowButton(
                onClick = {},
                containerColor = AppTheme.color.background.copy(alpha = .5f),
                shadowColor = AppTheme.color.queenMagenta,
                shadowRadius = 12.dp,
                shadowOffsetY = 0.dp
            )
            CloseShadowButton(
                onClick = {},
                shadowColor = AppTheme.color.queenRed,
                shadowRadius = 40.dp,
                shadowOffsetY = 0.dp
            )
            CloseShadowButton(
                onClick = {},
                containerColor = AppTheme.color.background,
                shadowColor = AppTheme.color.textPrimary,
                shadowRadius = 40.dp,
                shadowOffsetY = 0.dp
            )

        }
    }
}