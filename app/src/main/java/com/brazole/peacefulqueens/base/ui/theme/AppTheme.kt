package com.brazole.peacefulqueens.base.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val themeColors = Colors.create()

    CompositionLocalProvider(LocalColors provides themeColors) {
        val typography = Typography.create()

        CompositionLocalProvider(
            LocalTypography provides typography,
            content = content
        )
    }
}

object AppTheme {
    val typography: Typography
        @Composable
        get() = LocalTypography.current

    val color: Colors
        @Composable
        get() = LocalColors.current

}
