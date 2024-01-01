package com.anadolstudio.chronos.view.chronometer

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewClockUpTimerBinding
import com.anadolstudio.core.util.common.throttleClick
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick

class ChronometerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private companion object {
        const val START_TIME = 0L
    }

    private val binding: ViewClockUpTimerBinding
    private var inProgress: Boolean = false
    private var startTime: Long = START_TIME
    private var state = State.NONE

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_clock_up_timer, this)
        binding = ViewClockUpTimerBinding.bind(view)
        binding.chronometer.text = context.getString(R.string.global_chronometer_text, "00", "00")

        binding.chronometer.setOnChronometerTickListener { chronometer ->
            val time = SystemClock.elapsedRealtime() - chronometer.base

            val h = (time / 3600000).toInt()
            val m = (time - h * 3600000) / 60000
            val s = (time - h * 3600000 - m * 60000).toInt() / 1000

            val hh = if (h < 10) "0$h" else "$h"
            val mm = if (m < 10) "0$m" else "$m"
            val ss = if (s < 10) "0$s" else "$s"

            if (h > 0) {
                chronometer.text = context.getString(R.string.global_chronometer_text, hh, mm)
                if (!binding.secondsText.isVisible) binding.secondsText.isVisible = true
                binding.secondsText.text = ss
            } else {
                chronometer.text = context.getString(R.string.global_chronometer_text, mm, ss)
            }
        }

        setup()
    }

    private fun setState(state: State) {
        val (textId, colorId) = when (state) {
            State.NONE -> R.string.global_start to R.color.black
            State.IN_PROGRESS -> R.string.global_stop to R.color.black
            State.STOP -> R.string.global_start to R.color.gray
        }

        binding.addButton.isVisible = state == State.STOP
        binding.removeButton.isVisible = state == State.STOP
        if (state == State.NONE) binding.secondsText.isVisible = false

        binding.chronometerButton.apply {
            text = context.getString(textId)
            setTextColor(context.getColor(colorId))
            isEnabled = state != State.STOP
        }

        this.state = state
    }

    fun setup(startTime: Long = START_TIME, inProgress: Boolean = false) {
        this.startTime = startTime
        this.inProgress = inProgress

        binding.apply {

            val state = when {
                inProgress -> State.IN_PROGRESS
                else -> if (startTime == START_TIME) State.NONE else State.STOP
            }

            setState(state)

            if (inProgress) {
                chronometer.base = startTime
                chronometer.start()
            }
        }
    }

    fun addListeners(
        onAddButtonAction: () -> Unit,
        onRemoveButtonAction: () -> Unit,
        onStartButtonAction: () -> Unit,
        onStopButtonAction: () -> Unit,
    ) {
        binding.apply {
            addButton.scaleAnimationOnClick { onAddButtonAction.invoke() }
            removeButton.scaleAnimationOnClick { onRemoveButtonAction.invoke() }
            setupChronometerButton(onStartButtonAction, onStopButtonAction)
        }
    }

    private fun setupChronometerButton(onStartButtonAction: () -> Unit, onStopButtonAction: () -> Unit) {
        binding.chronometerButton.throttleClick {
            when (inProgress) {
                true -> {
                    onStopButtonAction.invoke()
                    binding.chronometer.stop()
                }

                false -> {
                    onStartButtonAction.invoke()
                    this.startTime = SystemClock.elapsedRealtime() - 59 * 60 * 1000L - 50 * 1000L
                    binding.chronometer.base = startTime
                    binding.chronometer.start()
                }
            }

            inProgress = !inProgress
            val newState = if (inProgress) State.IN_PROGRESS else State.STOP
            setState(newState)
        }
    }

    private enum class State {
        NONE,
        IN_PROGRESS,
        STOP
    }

}
