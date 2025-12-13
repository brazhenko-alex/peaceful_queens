package com.brazole.peacefulqueens.util.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchAndCollect(
    scope: CoroutineScope,
    collectAction: suspend (value: T) -> Unit
) = scope.launch {
    collect { collectAction.invoke(it) }
}

