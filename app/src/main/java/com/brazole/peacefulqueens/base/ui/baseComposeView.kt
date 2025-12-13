package com.brazole.peacefulqueens.base.ui

import android.content.Context
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.brazole.peacefulqueens.base.ui.theme.AppTheme

fun baseComposeView(context: Context, content: @Composable () -> Unit): View {
    return ComposeView(context).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AppTheme {
                content()
            }
        }
    }
}

