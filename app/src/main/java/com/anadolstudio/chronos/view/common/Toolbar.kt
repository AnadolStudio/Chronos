package com.anadolstudio.chronos.view.common

import android.content.Context
import android.util.AttributeSet
import com.anadolstudio.chronos.R
import com.anadolstudio.core.view.toolbar.BaseToolbar

class Toolbar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : BaseToolbar(context, attrs, defStyleAttr) {

    init {
        setTextAppearance(R.style.Serif_Black_Bold)
        setTitle(context.getString(R.string.app_name))
    }
}
