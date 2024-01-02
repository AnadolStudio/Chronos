package com.anadolstudio.chronos.view.stop_watcher

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewStopWatcherBinding
import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData.State

class StopWatcherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private companion object {
        const val ZERO_TIME = "00"
    }

    private val binding = ViewStopWatcherBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.secondsText.text = ZERO_TIME
        binding.chronometer.text = context.getString(R.string.global_chronometer_text, ZERO_TIME, ZERO_TIME)
    }

    private fun updateTimeTitle(startTime: Long, endTime: Long) {
        val time = Time(endTime - startTime)

        binding.chronometer.text =
            context.getString(R.string.global_chronometer_text, time.hoursString, time.minutesString)
        binding.secondsText.text = time.secondsString
    }

    fun setup(data: StopWatcherData) {
        binding.apply {
            val startTime = data.startTime ?: System.currentTimeMillis()
            val endTime = data.endTime ?: System.currentTimeMillis()

            updateTimeTitle(startTime, endTime)

            when (data.state) {
                State.IN_PROGRESS -> {
                    binding.chronometer.base = startTime

                    binding.chronometer.setOnChronometerTickListener { chronometer ->
                        updateTimeTitle(chronometer.base, System.currentTimeMillis())
                    }

                    binding.chronometer.start()
                }

                State.RESULT, State.DEFAULT -> {
                    binding.chronometer.setOnChronometerTickListener(null)
                    binding.chronometer.stop()
                }
            }

            binding.addButton.isVisible = data.state == State.RESULT
            binding.addButton.isEnabled = data.state == State.RESULT && data.deltaTime.minutes > 0
            binding.removeButton.isVisible = data.state == State.RESULT
        }
    }

    fun addListeners(onAddButtonAction: () -> Unit, onRemoveButtonAction: () -> Unit) {
        binding.apply {
            addButton.scaleAnimationOnClick { onAddButtonAction.invoke() }
            removeButton.scaleAnimationOnClick { onRemoveButtonAction.invoke() }
        }
    }

}
