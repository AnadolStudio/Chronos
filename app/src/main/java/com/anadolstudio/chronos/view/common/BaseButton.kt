package com.anadolstudio.chronos.view.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewBaseButtonBinding

class BaseButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewBaseButtonBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_base_button, this)
        binding = ViewBaseButtonBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.BaseButton, defStyleAttr, 0) {
            binding.title.text = getString(R.styleable.BaseButton_title) ?: context.getString(R.string.global_apply)
        }
    }
}

