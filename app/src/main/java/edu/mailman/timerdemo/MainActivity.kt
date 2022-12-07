package edu.mailman.timerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import edu.mailman.timerdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private var countDownTimer: CountDownTimer? = null
    private var timerDuration: Long = 30_000
    private var pauseOffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        binding?.tvTimer?.text = (timerDuration / 1000).toString()

        binding?.btnStart?.setOnClickListener {
            startTimer(pauseOffset)
        }

        binding?.btnPause?.setOnClickListener {
            pauseTimer()
        }

        binding?.btnRestart?.setOnClickListener {
            restartTimer()
        }

        binding?.btnReset?.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer(pauseOffsetL: Long) {
        if (countDownTimer == null) {
            countDownTimer = object : CountDownTimer(
                timerDuration - pauseOffsetL, 1000
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    pauseOffset = timerDuration - millisUntilFinished
                    binding?.tvTimer?.text =
                        (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    Toast.makeText(this@MainActivity,
                        "Timer is finished", Toast.LENGTH_LONG).show()
                    countDownTimer = null
                    pauseOffset = 0
                }
            }.start()
        }
    }

    private fun pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private fun restartTimer() {
        if (countDownTimer != null) {
            countDownTimer = null
            startTimer(pauseOffset)
        }
    }

    private fun resetTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            binding?.tvTimer?.text = (timerDuration / 1000).toString()
            countDownTimer = null
            pauseOffset = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}