package com.brazole.peacefulqueens.game.data

import com.brazole.peacefulqueens.util.Constants.BOARD_SIZE_DEFAULT
import com.brazole.peacefulqueens.util.PreferencesKeys.KEY_BOARD_SIZE
import com.brazole.peacefulqueens.util.PreferencesManager
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val preferencesManager: PreferencesManager
) {

    fun saveBoardSize(boardSize: Int) {
        preferencesManager.putInt(KEY_BOARD_SIZE, boardSize)
    }

    fun getBoardSize(): Int {
        return preferencesManager.getInt(KEY_BOARD_SIZE, BOARD_SIZE_DEFAULT)
    }
}

