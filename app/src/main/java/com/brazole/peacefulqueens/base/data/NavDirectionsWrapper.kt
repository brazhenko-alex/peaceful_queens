package com.brazole.peacefulqueens.base.data

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import kotlinx.parcelize.Parcelize

@Parcelize
class NavDirectionsWrapper(
    override val actionId: Int,
    override val arguments: Bundle = bundleOf(),
) : NavDirections, Parcelable
