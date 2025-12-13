package com.brazole.peacefulqueens.base.data

import androidx.annotation.StringRes

object NavigateBack : Event
data class ShowMessage(val message: String) : Event
data class ShowMessageRes(@StringRes val message: Int) : Event
