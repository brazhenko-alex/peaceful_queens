package com.brazole.peacefulqueens.game.viewModel

import androidx.lifecycle.viewModelScope
import com.brazole.peacefulqueens.base.viewModel.BaseViewModel
import com.brazole.peacefulqueens.bestScores.data.BestScoresRepository
import com.brazole.peacefulqueens.game.data.Cell
import com.brazole.peacefulqueens.game.data.GameRepository
import com.brazole.peacefulqueens.game.data.GameUiState
import com.brazole.peacefulqueens.game.data.ShowBestScoresDialog
import com.brazole.peacefulqueens.game.domain.GameEngine
import com.brazole.peacefulqueens.util.SoundPlayer
import com.brazole.peacefulqueens.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameEngine: GameEngine,
    private val gameRepository: GameRepository,
    private val bestScoresRepository: BestScoresRepository,
    private val timeFormatter: TimeFormatter,
    private val soundPlayer: SoundPlayer
) : BaseViewModel<GameUiState>(GameUiState()) {

    private var timerJob: Job? = null
    private var isInitialized = false
    private var elapsedTime: Long = 0L

    init {
        if (!isInitialized) {
            val savedBoardSize = gameRepository.getBoardSize()
            initGame(savedBoardSize)
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
                showResetConfirmDialog = false,
                isGameFinished = false
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
        if (currentState.isGameFinished) return

        if (clickedCell.hasQueen.not()) {
            soundPlayer.playMoveSound()
        }

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
                        elapsedTimeFormatted = timeFormatter.formatTime(++elapsedTime)
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
            updateUiState(
                currentState.copy(
                    showGameWonDialog = true,
                    isGameFinished = true
                )
            )
        }
    }

    fun onGameWonDialogDismiss() = updateUiState(
        getUiStateValue().copy(
            showGameWonDialog = false,
            showMenuDialog = true
        )
    )

    fun onNewGameConfirmDialogConfirm(newBoardSize: Int) {
        gameRepository.saveBoardSize(newBoardSize)
        initGame(newBoardSize)
    }

    fun onHintClick() = updateUiState(getUiStateValue().copy(hintMode = getUiStateValue().hintMode.not()))

    fun onBestScoresView() = sendEvent(ShowBestScoresDialog(getUiStateValue().queensRemaining))

    fun onSettingsClick() = updateUiState(getUiStateValue().copy(showMenuDialog = true))

    fun onMenuDialogDismiss() = updateUiState(getUiStateValue().copy(showMenuDialog = false))

    fun onNewGameConfirmDialogShow() = updateUiState(getUiStateValue().copy(showNewGameConfirmDialog = true))

    fun onNewGameConfirmDialogDismiss() = updateUiState(getUiStateValue().copy(showNewGameConfirmDialog = false))

    fun onResetClick() = initGame(getUiStateValue().boardSize)

    fun onResetConfirmDialogShow() = updateUiState(getUiStateValue().copy(showResetConfirmDialog = true))

    fun onResetConfirmDialogConfirm() = initGame(getUiStateValue().boardSize)

    fun onResetConfirmDialogDismiss() = updateUiState(getUiStateValue().copy(showResetConfirmDialog = false))

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        soundPlayer.release()
    }
}

