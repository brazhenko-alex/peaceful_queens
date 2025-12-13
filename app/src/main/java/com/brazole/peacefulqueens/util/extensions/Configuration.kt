package com.brazole.peacefulqueens.util.extensions

import android.content.res.Configuration
import androidx.compose.ui.unit.dp
import com.brazole.peacefulqueens.base.ui.theme.Dimens

val Configuration.isTablet: Boolean
    get() = screenWidthDp.dp > Dimens.tabletBreakpoint

