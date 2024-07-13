package com.anadolstudio.chronos.view.diagram

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.utils.animation.AnimateUtil
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

        var previousAngle = 0F
        drawProgressDataList.forEach { progressData ->
            val ratio = progressData.value / totalValue.toFloat()
            val sweepAngle = CIRCLE * ratio

            defaultPaint.color = progressData.color
            canvas.drawRoundLine(previousAngle, sweepAngle, defaultPaint)

            previousAngle += sweepAngle
        }

        drawProgressDataList.ifEmpty {
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
