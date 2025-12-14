package com.brazole.peacefulqueens.base.ui.composables

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.brazole.peacefulqueens.base.ui.theme.AppTheme

@Composable
fun NeonGlowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    glowColor: Color = AppTheme.color.queenMagenta,
    containerColor: Color = AppTheme.color.background,
    glowRadius: Dp = 12.dp,
) {
    val glowRadiusPx = with(LocalDensity.current) { glowRadius.toPx() }

    val paint = remember(glowColor, glowRadiusPx) {
        Paint().apply {
            color = glowColor.toArgb()
            isAntiAlias = true
            setShadowLayer(
                glowRadiusPx,
                0f, 0f,
                glowColor.toArgb()
            )
        }
    }

    Box(
        modifier = modifier
            .padding(glowRadius)
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val rect = RectF(0f, 0f, size.width, size.height)

                    canvas.nativeCanvas.drawRoundRect(
                        /* rect = */ rect,
                        /* rx = */ size.height / 2,
                        /* ry = */ size.height / 2,
                        /* paint = */ paint
                    )
                }
            }
            .clip(CircleShape)
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 32.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = glowColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
    }
}

@Preview
@Composable
private fun NeonButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.padding(30.dp)) {
                NeonGlowButton(
                    modifier = Modifier
                        .width(420.dp)
                        .height(80.dp),
                    text = "CLEAR ALL",
                    onClick = {})
            }
            NeonGlowButton(
                text = "START GAME",
                onClick = {})

            NeonGlowButton(
                text = "START GAME",
                glowColor = AppTheme.color.queenRedGlow,
                onClick = {})

            NeonGlowButton(
                text = "",
                glowColor = AppTheme.color.queenRed,
                onClick = {})
        }
    }
}