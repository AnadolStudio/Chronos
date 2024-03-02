package com.anadolstudio.chronos.view.diagram

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.utils.util.common.dpToPx

class LineProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private companion object {
        val STROKE_WIDTH = 8F.dpToPx()
    }

    init {
        minimumHeight = STROKE_WIDTH.toInt()
        background = ColorDrawable(Color.TRANSPARENT)
    }

    private val defaultColor = context.getColor(R.color.disableBackground)
    private val defaultPaint: Paint = ProgressPaint(defaultColor)

    private var progressDataList: List<ProgressData> = emptyList()
    private val totalValue get() = progressDataList.sumOf { it.value }

    fun setup(progressDataList: List<ProgressData>) {
        this.progressDataList = progressDataList
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var previousEndX = 0F
        progressDataList.forEachIndexed { index, progressData ->
            val isStart = index == 0
            val isEnd = index == progressDataList.lastIndex
            val ratio = progressData.value / totalValue.toFloat()
            val endX = previousEndX + width * ratio

            defaultPaint.color = progressData.color
            canvas.drawRoundLine(previousEndX, endX, height / 2F, defaultPaint, isStart, isEnd)

            previousEndX = endX
        }

        progressDataList.ifEmpty {
            defaultPaint.color = defaultColor
            canvas.drawRoundLine(previousEndX, width.toFloat(), height / 2F, defaultPaint, true, true)
        }
    }

    private fun Canvas.drawRoundLine(startX: Float, endX: Float, y: Float, paint: Paint, isStart: Boolean, isEnd: Boolean) {
        val correctStartX = if (isStart) startX + STROKE_WIDTH else startX
        val correctEndX = if (isEnd) endX - STROKE_WIDTH else endX

        paint.strokeCap = Paint.Cap.ROUND
        if (isStart) drawLine(correctStartX, y, correctStartX, y, paint)
        if (isEnd) drawLine(correctEndX, y, correctEndX, y, paint)

        paint.strokeCap = Paint.Cap.BUTT
        drawLine(correctStartX, y, correctEndX, y, paint)
    }

}
