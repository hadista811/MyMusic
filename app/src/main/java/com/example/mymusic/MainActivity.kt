package com.example.mymusic

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.yoasobialbum)
        val seekbar: SeekBar = findViewById(R.id.seekbar)
        val play_btn: ImageButton = findViewById(R.id.play_btn)

        seekbar.progress = 0
        seekbar.max = mediaPlayer.duration

        play_btn.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                play_btn.setImageResource(R.drawable.ic_pause_black_24)
            } else {
                mediaPlayer.pause()
                play_btn.setImageResource(R.drawable.ic_play_arrow_black_24)
            }
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        runnable = Runnable {
            seekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        mediaPlayer.setOnCompletionListener {
            play_btn.setImageResource(R.drawable.ic_play_arrow_black_24)
            seekbar.progress = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }
}
