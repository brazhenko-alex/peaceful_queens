package com.brazole.peacefulqueens.game.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.base.ui.theme.AppTheme

@Composable
fun CrownComposable(
    modifier: Modifier = Modifier,
    brightColor: Color = AppTheme.color.queenMagenta,
    coreColor: Color = AppTheme.color.queenMagentaGlow,
    glowRadius: Dp = 8.dp,
    strokeWidth: Dp = 2.dp
) {
    Canvas(modifier = modifier.size(200.dp)) {
        val width = size.width
        val height = size.height

        val crownBodyPath = Path().apply {
            moveTo(x = width * 0.2f, y = height * 0.65f)
            lineTo(x = width * 0.15f, y = height * 0.3f)
            lineTo(x = width * 0.35f, y = height * 0.5f)
            lineTo(x = width * 0.5f, y = height * 0.15f)
            lineTo(x = width * 0.65f, y = height * 0.5f)
            lineTo(x = width * 0.85f, y = height * 0.3f)
            lineTo(x = width * 0.8f, y = height * 0.65f)
        }

        val baseLineStart = Offset(width * 0.2f, height * 0.75f)
        val baseLineEnd = Offset(width * 0.8f, height * 0.75f)

        drawIntoCanvas { canvas ->
            val paint = Paint().asFrameworkPaint()

            paint.apply {
                this.color = brightColor.toArgb()
                this.style = android.graphics.Paint.Style.STROKE // Draw as lines!
                this.strokeWidth = strokeWidth.toPx() * 2.5f
                this.strokeCap = android.graphics.Paint.Cap.ROUND
                this.strokeJoin = android.graphics.Paint.Join.ROUND

                this.setShadowLayer(
                    /* radius = */ glowRadius.toPx(),
                    /* dx = */ 0f, /* dy = */ 0f,
                    /* shadowColor = */ brightColor.toArgb()
                )
            }
            canvas.nativeCanvas.drawPath(crownBodyPath.asAndroidPath(), paint)
            canvas.nativeCanvas.drawLine(
                baseLineStart.x, baseLineStart.y,
                baseLineEnd.x, baseLineEnd.y,
                paint
            )

            paint.apply {
                this.color = coreColor.toArgb()
                this.style = android.graphics.Paint.Style.STROKE
                this.strokeWidth = strokeWidth.toPx()
                this.clearShadowLayer()
            }

            canvas.nativeCanvas.drawPath(crownBodyPath.asAndroidPath(), paint)
            canvas.nativeCanvas.drawLine(
                baseLineStart.x, baseLineStart.y,
                baseLineEnd.x, baseLineEnd.y,
                paint
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewPurpleComposable() {
    AppTheme {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CrownComposable()
            CrownComposable()
            CrownComposable(
                modifier = Modifier.size(24.dp),
                brightColor = AppTheme.color.queenRedGlow,
                coreColor = Color(0xFFf98f8f),
                glowRadius = 4.dp,
                strokeWidth = 1.dp
            )

            CrownComposable(
                modifier = Modifier.size(24.dp),
                brightColor = AppTheme.color.queenRed,
                coreColor = AppTheme.color.queenRedGlow,
                glowRadius = 4.dp,
                strokeWidth = 1.dp
            )

            CrownComposable()
        }
    }
}
