package com.anadolstudio.chronos.view.function

import androidx.annotation.StringRes

interface Textable {
    fun setText(text: String?)
    fun setText(@StringRes textRes: Int)
}