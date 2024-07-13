package com.anadolstudio.chronos.view.diagram

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.utils.animation.AnimateUtil
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
    private var drawProgressDataList: List<ProgressData> = progressDataList
    private val totalValue get() = drawProgressDataList.sumOf { it.value }
    private var animator: ValueAnimator? = null

    fun setup(progressDataList: List<ProgressData>) {
        val oldProgressDataMap = this.progressDataList.associateBy { it.color }
        val newProgressDataMap = progressDataList.associateBy { it.color }
        this.progressDataList = progressDataList

        val allColorsSet = (oldProgressDataMap.keys + newProgressDataMap.keys)
                .sortedBy { it != defaultColor }
                .toSet()

        val map = mutableMapOf<Int, Pair<ProgressData?, ProgressData?>>()
        allColorsSet.forEach { color ->
            map[color] = Pair(oldProgressDataMap[color], newProgressDataMap[color])
        }

        animatedChange(map)
    }

    private fun animatedChange(progressDataMap: Map<Int, Pair<ProgressData?, ProgressData?>>) {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0F, 1F).apply {
            duration = AnimateUtil.DURATION_NORMAL

            addUpdateListener { valueAnimator ->
                drawProgressDataList = progressDataMap
                        .map { (color, oldNewPair) ->
                            val oldValue = oldNewPair.first?.value ?: 0
                            val newValue = oldNewPair.second?.value ?: 0
                            val delta = newValue - oldValue
                            val value = (oldValue + delta * valueAnimator.animatedFraction).toInt()

                            ProgressData(color, value)
                        }
                        .sortedBy { it.color == defaultColor }
                        .filter { progressData -> progressData.value != 0 }

                invalidate()
            }

            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var previousEndX = 0F
        drawProgressDataList.forEachIndexed { index, progressData ->
            val isStart = index == 0
            val isEnd = index == drawProgressDataList.lastIndex
            val ratio = progressData.value / totalValue.toFloat()
            val endX = previousEndX + width * ratio

            defaultPaint.color = progressData.color
            canvas.drawRoundLine(previousEndX, endX, height / 2F, defaultPaint, isStart, isEnd)

            previousEndX = endX
        }

        drawProgressDataList.ifEmpty {
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
