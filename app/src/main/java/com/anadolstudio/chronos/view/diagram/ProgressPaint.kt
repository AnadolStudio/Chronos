package com.anadolstudio.chronos.view.diagram

import android.graphics.Paint
import com.anadolstudio.core.util.common.dpToPx

class ProgressPaint(color: Int, strokeWidth: Float = STROKE_WIDTH) : Paint(ANTI_ALIAS_FLAG) {

    private companion object {
        val STROKE_WIDTH = 8F.dpToPx()
    }

    init {
        style = Style.STROKE
        this.strokeWidth = strokeWidth
        this.color = color
    }
}