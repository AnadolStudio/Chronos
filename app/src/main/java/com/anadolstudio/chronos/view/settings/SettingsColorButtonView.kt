package com.anadolstudio.chronos.view.settings

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewSettingsColorButtonBinding
import com.anadolstudio.chronos.databinding.ViewSettingsViewPagerItemBinding

class SettingsColorButtonView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewSettingsColorButtonBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_settings_color_button, this)
        binding = ViewSettingsColorButtonBinding.bind(view)
    }

    fun setColor(color: Int) {
        binding.colorContainer.backgroundTintList = ColorStateList.valueOf(color)
    }

}
