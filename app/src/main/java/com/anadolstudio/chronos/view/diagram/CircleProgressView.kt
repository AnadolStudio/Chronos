package com.anadolstudio.chronos.view.diagram

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.utils.util.common.dpToPx

class CircleProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private companion object {
        const val CIRCLE = 360F
        val MIN_SIZE = 175.dpToPx()
        val STROKE_WIDTH = 16F.dpToPx()
    }

    init {
        minimumHeight = MIN_SIZE
        minimumWidth = MIN_SIZE
        background = ColorDrawable(Color.TRANSPARENT)
    }

    private val defaultColor = context.getColor(R.color.disableBackground)
    private val defaultPaint: Paint = ProgressPaint(defaultColor, STROKE_WIDTH)
    private var progressDataList: List<ProgressData> = emptyList()
    private val totalValue get() = progressDataList.sumOf { it.value }

    fun setup(progressDataList: List<ProgressData>) {
        this.progressDataList = progressDataList
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var previousAngle = 0F
        progressDataList.forEach { progressData ->
            val ratio = progressData.value / totalValue.toFloat()
            val sweepAngle = CIRCLE * ratio

            defaultPaint.color = progressData.color
            canvas.drawRoundLine(previousAngle, sweepAngle, defaultPaint)

            previousAngle += sweepAngle
        }

        progressDataList.ifEmpty {
            defaultPaint.color = defaultColor
            canvas.drawRoundLine(previousAngle, CIRCLE, defaultPaint)
        }
    }

    private fun Canvas.drawRoundLine(startAngle: Float, sweepAngle: Float, paint: Paint) {
        val minSide = minOf(width - paint.strokeWidth, height - paint.strokeWidth)
        val offsetX = (width - minSide) / 2
        val offsetY = (height - minSide) / 2
        val rect = RectF(offsetX, offsetY, minSide + offsetX, minSide + offsetY)

        drawArc(rect, startAngle, sweepAngle, false, paint)
    }

}
