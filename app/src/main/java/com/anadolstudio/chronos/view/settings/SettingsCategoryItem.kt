package com.anadolstudio.chronos.view.settings

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewSettingsCategoryItemBinding

class SettingsCategoryItem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewSettingsCategoryItemBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_settings_category_item, this)
        binding = ViewSettingsCategoryItemBinding.bind(view)
    }

    fun setTitle(title: String) {
        binding.textView.text = title
    }

    fun setColor(color: Int) {
        binding.textContainer.backgroundTintList = ColorStateList.valueOf(color)
    }

}
