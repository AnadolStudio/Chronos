package com.anadolstudio.chronos.view.function

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

interface Imageble {
    fun setDrawable(drawable: Drawable?)
    fun setDrawable(@DrawableRes drawableRes: Int)
}