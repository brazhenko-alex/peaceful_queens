package com.brazole.peacefulqueens.util

import android.content.Context
import android.media.MediaPlayer
import com.brazole.peacefulqueens.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundPlayer @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var moveMediaPlayer: MediaPlayer? = null

    fun playMoveSound() {
        try {
            moveMediaPlayer?.release()
            moveMediaPlayer = MediaPlayer.create(context, R.raw.move).apply {
                setOnCompletionListener { mp ->
                    mp.release()
                }
                start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun release() {
        moveMediaPlayer?.release()
        moveMediaPlayer = null
    }
}

