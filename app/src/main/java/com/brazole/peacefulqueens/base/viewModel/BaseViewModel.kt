package com.brazole.peacefulqueens.base.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brazole.peacefulqueens.base.data.Event
import com.brazole.peacefulqueens.base.data.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<T : UiState>(initialState: T) : ViewModel() {

    private val _uiState: MutableState<T> = mutableStateOf(initialState)
    private val uiState: State<T> = _uiState

    private val _events = Channel<Event>()
    val events = _events.receiveAsFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    protected val safeViewModelScope = CoroutineScope(viewModelScope.coroutineContext + exceptionHandler)

    protected fun updateUiState(newState: T) {
        _uiState.value = newState
    }

    fun getUiStateValue(): T = _uiState.value

    protected fun sendEvent(event: Event) {
        safeViewModelScope.launch {
            _events.send(event)
        }
    }
}