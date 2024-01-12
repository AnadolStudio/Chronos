package com.anadolstudio.chronos.view.diagram

import android.graphics.Paint
import com.anadolstudio.core.util.common.dpToPx

class ProgressPaint(color: Int) : Paint(ANTI_ALIAS_FLAG) {

    private companion object {
        val STROKE_WIDTH = 8F.dpToPx()
    }

    init {
        style = Style.STROKE
        strokeWidth = STROKE_WIDTH
        this.color = color
    }
}