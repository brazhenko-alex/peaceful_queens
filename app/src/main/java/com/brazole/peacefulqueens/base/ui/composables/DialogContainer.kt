package com.brazole.peacefulqueens.base.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.base.ui.theme.AppTheme
import com.brazole.peacefulqueens.base.ui.theme.Dimens
import com.brazole.peacefulqueens.util.extensions.isTablet

@Composable
fun AppDialogContainer(
    modifier: Modifier = Modifier,
    maxHeight: Dp = Dimens.dialogMaxHeight,
    content: @Composable BoxScope.() -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val shape = RoundedCornerShape(Dimens.dialogCornerRadius)

    val maxWidth = if (configuration.isTablet) {
        Dimens.dialogMaxWidthTablet.coerceAtMost(screenWidthDp)
    } else {
        (screenWidthDp).coerceAtMost(screenWidthDp)
    }

    Box(
        modifier = modifier
            .widthIn(
                min = Dimens.dialogMinWidth,
                max = maxWidth
            )
            .background(
                color = AppTheme.color.backgroundDialog,
                shape = shape
            )
            .clip(shape)
            .heightIn(max = maxHeight),
        content = content
    )
}

