package com.brazole.peacefulqueens.base.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(
    val title: TextStyle,
    val text: TextStyle,
    val textMedium: TextStyle,
    val smallText: TextStyle,
) {
    companion object {

        @Composable
        fun create() = Typography(
            title = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = AppTheme.color.textPrimary,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            text = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = AppTheme.color.textPrimary,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            textMedium = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = AppTheme.color.textPrimary,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            smallText = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = AppTheme.color.textPrimary,
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
        )
    }
}

val LocalTypography = staticCompositionLocalOf {
    Typography(
        title = TextStyle.Default,
        text = TextStyle.Default,
        smallText = TextStyle.Default,
        textMedium = TextStyle.Default,
    )
}

@Composable
private fun Preview0() {
    AppTheme {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxHeight()
        ) {
            TypographyBlock("Title1", AppTheme.typography.title)
            TypographyBlock("Text", AppTheme.typography.text)
            TypographyBlock("Text Medium", AppTheme.typography.textMedium)
            TypographyBlock("Small Text", AppTheme.typography.smallText)
        }
    }
}

@Composable
private fun TypographyBlock(textStyleName: String, style: TextStyle) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = textStyleName,
            style = style
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TypographyPreview1() {
    AppTheme {
        Box(modifier = Modifier.background(color = AppTheme.color.background)) {
            Preview0()
        }
    }
}
