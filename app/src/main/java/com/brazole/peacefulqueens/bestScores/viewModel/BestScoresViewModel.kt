package com.brazole.peacefulqueens.bestScores.viewModel

import androidx.lifecycle.SavedStateHandle
import com.brazole.peacefulqueens.base.viewModel.BaseViewModel
import com.brazole.peacefulqueens.bestScores.data.BestScoresRepository
import com.brazole.peacefulqueens.bestScores.data.BestScoresUiState
import com.brazole.peacefulqueens.bestScores.ui.BestScoresDialogFragment.Companion.ARG_QUEENS_LEFT
import com.brazole.peacefulqueens.util.Constants.NO_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BestScoresViewModel @Inject constructor(
    private val bestScoresRepository: BestScoresRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<BestScoresUiState>(BestScoresUiState()) {

    init {
        loadBestScores()
    }

    fun loadBestScores() {
        safeViewModelScope.launch {
            updateUiState(getUiStateValue().copy(isLoading = true))

            val scores = bestScoresRepository.getAllBestScores().toImmutableList()
            val queensLeft = savedStateHandle.get<Int>(ARG_QUEENS_LEFT) ?: NO_ID

            updateUiState(
                getUiStateValue().copy(
                    queensLeft = queensLeft,
                    scores = scores,
                    isLoading = false
                )
            )
        }
    }

    fun onClearAll() {
        updateUiState(getUiStateValue().copy(showClearConfirmationDialog = true))
    }

    fun onClearDialogDismiss() {
        updateUiState(getUiStateValue().copy(showClearConfirmationDialog = false))
    }

    fun onClearDialogConfirm() {
        safeViewModelScope.launch {
            updateUiState(getUiStateValue().copy(showClearConfirmationDialog = false))
            bestScoresRepository.clearAllBestScores()
            loadBestScores()
        }
    }
}

