package com.brazole.peacefulqueens.game.viewModel

import androidx.lifecycle.viewModelScope
import com.brazole.peacefulqueens.base.viewModel.BaseViewModel
import com.brazole.peacefulqueens.bestScores.data.BestScoresRepository
import com.brazole.peacefulqueens.game.data.Cell
import com.brazole.peacefulqueens.game.data.GameUiState
import com.brazole.peacefulqueens.game.data.ShowBestScoresDialog
import com.brazole.peacefulqueens.game.domain.GameEngine
import com.brazole.peacefulqueens.util.Constants.BOARD_SIZE_DEFAULT
import com.brazole.peacefulqueens.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameEngine: GameEngine,
    private val bestScoresRepository: BestScoresRepository,
    private val timeFormatter: TimeFormatter
) : BaseViewModel<GameUiState>(GameUiState()) {

    private var timerJob: Job? = null
    private var isInitialized = false
    private var elapsedTime: Long = 0L

    init {
        if (!isInitialized) {
            initGame(BOARD_SIZE_DEFAULT)
        }
    }

    private fun initGame(boardSize: Int) {
        stopTimer()
        val initialCells = gameEngine.initializeBoard(boardSize)
        updateUiState(
            GameUiState(
                boardSize = boardSize,
                queensRemaining = boardSize,
                cellList = initialCells,
                showNewGameConfirmDialog = false,
                showMenuDialog = false,
                showGameWonDialog = false,
                showResetConfirmDialog = false
            )
        )
        elapsedTime = 0L
        isInitialized = true
    }

    fun resumeTimerIfNeeded() {
        if (timerJob == null && elapsedTime > 0) {
            startTimer()
        }
    }

    fun onCellClick(clickedCell: Cell) {
        val currentState = getUiStateValue()
        if (currentState.showGameWonDialog) return

        val updatedCells = gameEngine.onCellClick(currentState.cellList, clickedCell)

        val queensCount = updatedCells.count { it.hasQueen }
        val queensRemaining = currentState.boardSize - queensCount

        updateUiState(
            currentState.copy(
                cellList = updatedCells,
                queensRemaining = queensRemaining
            )
        )

        if (timerJob == null && queensCount > 0) {
            startTimer()
        }

        checkWinCondition()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                updateUiState(
                    getUiStateValue().copy(
                        elapsedTimeFormatted = timeFormatter.formatTime(elapsedTime++)
                    )
                )
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun checkWinCondition() {
        val currentState = getUiStateValue()
        val isWon = gameEngine.isGameWon(currentState.cellList)

        if (isWon) {
            stopTimer()
            bestScoresRepository.saveBestScore(currentState.boardSize, elapsedTime)
            updateUiState(currentState.copy(showGameWonDialog = true))
        }
    }

    fun onHintClick() = updateUiState(getUiStateValue().copy(hintMode = getUiStateValue().hintMode.not()))

    fun onGameWonDialogDismiss() = updateUiState(getUiStateValue().copy(showGameWonDialog = false))

    fun onBestScoresView() = sendEvent(ShowBestScoresDialog(getUiStateValue().queensRemaining))

    fun onSettingsClick() = updateUiState(getUiStateValue().copy(showMenuDialog = true))

    fun onMenuDialogDismiss() = updateUiState(getUiStateValue().copy(showMenuDialog = false))

    fun onNewGameConfirmDialogShow() = updateUiState(getUiStateValue().copy(showNewGameConfirmDialog = true))

    fun onNewGameConfirmDialogConfirm(newBoardSize: Int) = initGame(newBoardSize)

    fun onNewGameConfirmDialogDismiss() = updateUiState(getUiStateValue().copy(showNewGameConfirmDialog = false))

    fun onResetClick() = initGame(getUiStateValue().boardSize)

    fun onResetConfirmDialogShow() = updateUiState(getUiStateValue().copy(showResetConfirmDialog = true))

    fun onResetConfirmDialogConfirm() = initGame(getUiStateValue().boardSize)

    fun onResetConfirmDialogDismiss() = updateUiState(getUiStateValue().copy(showResetConfirmDialog = false))

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}

