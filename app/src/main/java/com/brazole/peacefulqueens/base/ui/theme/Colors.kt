package com.brazole.peacefulqueens.base.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
@SuppressWarnings("MagicNumber")
data class Colors(
    val cellLight: Color,
    val cellDark: Color,
    val cellBorder: Color,
    val conflictRed: Color,
    val conflictBackground: Color,
    val winGreen: Color,
    val background: Color,
    val backgroundDialog: Color,
    val backgroundDark: Color,
    val surface: Color,
    val iconTint: Color,
    val error: Color,
    val textPrimary: Color,
    val queenRed: Color,
    val queenRedGlow: Color,
    val queenMagenta: Color,
    val queenMagentaGlow: Color,
    val divider: Color
) {

    companion object {

        @Composable
        fun create() = Colors(
            cellLight = Color(0xFF44495D),
            cellDark = Color(0xFF2B333E),
            cellBorder = Color(0xFFECEBFD),
            conflictRed = Color(0xFFFF5252),
            conflictBackground = Color(0x33FF5252),
            winGreen = Color(0xFF5B8A3C),
            background = Color(0xFF1F232F),
            backgroundDialog = Color(0xFF242736),
            backgroundDark = Color(0xFF20212E) ,
            surface = Color(0xFF27232F),
            iconTint = Color(0xFFA2A7BB),
            error = Color(0xFFFF5252),
            textPrimary = Color(0xFFD5D9D5),
            queenRed = Color(0xFFF98F8F),
            queenRedGlow = Color(0x99FF5252),
            queenMagenta = Color(0xFFC46FEE),
            queenMagentaGlow = Color(0x99A855F7),
            divider = Color(0xFF444444)
        )
    }
}

val LocalColors = staticCompositionLocalOf {
    Colors(
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
        Color.Unspecified,
    )
}

@Preview(showBackground = true, heightDp = 1500, widthDp = 640)
@Composable
private fun ColorsPreview() {
    AppTheme {
        val colors = Colors.create()
        Column(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(16.dp),
        ) {
            val colorFields = colors::class.java.declaredFields
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    itemsIndexed(colorFields) { index, colorField ->

                        colorField.isAccessible = true
                        if (colorField.type == Long::class.java) {
                            val colorValue = colorField.get(colors) as Long
                            val colorDark = (((colorValue.toULong() shr 32) and 0xFFFFFFFFUL).toLong())
                            Column {
                                ColorBlock(
                                    colorName = colorField.name,
                                    colorDark = Color(colorDark),
                                )
                                Spacer(Modifier.size(24.dp))
                            }
                        }

                    }
                }
            )
        }
    }
}

@SuppressWarnings("MagicNumber")
@Composable
private fun ColorBlock(
    colorName: String,
    colorDark: Color,
) {
    Row(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.5f),
            text = colorName,
            color = Color.White,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(4.dp))
                .background(colorDark)
                .fillMaxHeight()
        )
    }
}
