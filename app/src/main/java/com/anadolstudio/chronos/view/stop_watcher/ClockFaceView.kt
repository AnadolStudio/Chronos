package com.anadolstudio.chronos.view.stop_watcher

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewClockFaceBinding
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData.State
import com.anadolstudio.utils.util.common.dpToPx
import com.anadolstudio.utils.util.data_time.Time
import com.anadolstudio.utils.util.extentions.centerX
import com.anadolstudio.utils.util.extentions.centerY
import java.util.concurrent.TimeUnit

class ClockFaceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private companion object {
        const val ZERO_TIME = "00"
        val STROKE_WIDTH = 2F.dpToPx()
        const val ANGLE_FROM_TOP = -90F
    }

    init {
        setWillNotDraw(false)
    }

    private val binding = ViewClockFaceBinding.inflate(LayoutInflater.from(context), this)

    private val mainPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = context.getColor(R.color.colorPrimary)
        }
    }

    private val secondsPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = context.getColor(R.color.colorAccent)
            style = Paint.Style.STROKE
            strokeWidth = STROKE_WIDTH
            strokeCap = Paint.Cap.ROUND
        }
    }

    private var angle: Float = 0F
    private var drawAngle: Float = angle
    private var animator: ValueAnimator? = null

    init {
        binding.secondsText.text = ZERO_TIME
        binding.chronometer.text = context.getString(R.string.global_chronometer_text, ZERO_TIME, ZERO_TIME)
    }

    private fun updateTimeTitle(startTime: Long, endTime: Long) {
        val time = Time(endTime - startTime)

        binding.chronometer.text =
                context.getString(R.string.global_chronometer_text, time.hoursString, time.minutesString)
        binding.secondsText.text = time.secondsString
        val oldAngle = angle
        val correctSeconds = if (time.seconds == 0) 60 else time.seconds
        angle = (correctSeconds / 60F) * 360

        if (oldAngle != angle) {
            animatedChange(oldAngle % 360, angle)
        }
    }

    private fun animatedChange(oldAngle: Float, angle: Float) {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(oldAngle, angle).apply {
            interpolator = LinearInterpolator()
            duration = TimeUnit.SECONDS.toMillis(1)
            addUpdateListener { valueAnimator ->
                drawAngle = valueAnimator.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    fun setup(data: StopWatcherData) {
        binding.apply {
            val startTime = data.startTime ?: System.currentTimeMillis()
            val endTime = data.endTime ?: System.currentTimeMillis()

            binding.chronometer.base = startTime
            updateTimeTitle(startTime, endTime)

            when (data.state) {
                State.IN_PROGRESS -> {
                    binding.chronometer.setOnChronometerTickListener { chronometer ->
                        updateTimeTitle(chronometer.base, System.currentTimeMillis())
                    }

                    binding.chronometer.start()
                }

                State.RESULT, State.DEFAULT -> {
                    binding.chronometer.onChronometerTickListener = null
                    binding.chronometer.stop()
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val minSide = minOf(width, height)

        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), minSide / 2F, mainPaint)

        canvas.drawArc(
                /*left*/ 0F + STROKE_WIDTH / 2,
                /*top*/ 0F + STROKE_WIDTH / 2,
                /*right*/ width.toFloat() - STROKE_WIDTH / 2,
                /*bottom*/ height.toFloat() - STROKE_WIDTH / 2,
                /*startAngle*/ ANGLE_FROM_TOP,
                /*sweepAngle*/ drawAngle,
                /*useCenter*/ false,
                /*paint*/ secondsPaint,
        )
    }

}
