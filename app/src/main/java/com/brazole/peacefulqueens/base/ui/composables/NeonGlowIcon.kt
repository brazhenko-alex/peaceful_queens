package com.brazole.peacefulqueens.base.ui.composables

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.R
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens

@Composable
fun NeonGlowIcon(
    painter: Painter,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = Dimens.iconButtonSize,
    glowColor: Color = AppTheme.color.queenMagenta,
    containerColor: Color = AppTheme.color.black.copy(alpha = .9f),
    glowRadius: Dp = 4.dp,
) {
    val glowRadiusPx = with(LocalDensity.current) { glowRadius.toPx() }
    val iconSize = buttonSize * 0.6f

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
            .size(buttonSize)
            .drawBehind {
                drawIntoCanvas { canvas ->
                    val diameter = size.minDimension
                    val rect = RectF(
                        /* left = */ (size.width - diameter) / 2,
                        /* top = */ (size.height - diameter) / 2,
                        /* right = */ (size.width + diameter) / 2,
                        /* bottom = */ (size.height + diameter) / 2
                    )

                    canvas.nativeCanvas.drawOval(
                        /* oval = */ rect,
                        /* paint = */ paint
                    )
                }
            }
            .clip(CircleShape)
            .background(containerColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            tint = glowColor.copy(alpha = 0.8f),
            modifier = Modifier.size(iconSize)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultQueenMagentaPreview98() {
    AppTheme {
        Column {
            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = "",
                containerColor = AppTheme.color.background,
                onClick = {},
            )
            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_schedule),
                contentDescription = "",
                onClick = {},
            )
            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_schedule),
                contentDescription = "",
                onClick = {},
                glowColor = AppTheme.color.iconTint,
            )
            NeonGlowIcon(
                painter = painterResource(R.drawable.ic_trophy),
                contentDescription = "",
                onClick = {},
                modifier = Modifier,
                buttonSize = 42.dp,
                glowColor = AppTheme.color.queenRed,
                //            containerColor = AppTheme.color.queenRed,
                glowRadius = 8.dp
            )
        }
    }
}