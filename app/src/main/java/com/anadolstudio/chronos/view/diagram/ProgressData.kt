package com.anadolstudio.chronos.view.diagram

import android.graphics.Paint

data class ProgressData(
        val color: Int,
        val value: Int
) {
    val paint: Paint = ProgressPaint(color)
}