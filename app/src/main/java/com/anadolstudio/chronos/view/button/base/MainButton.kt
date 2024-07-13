package com.anadolstudio.chronos.view.button.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewMainButtonBinding

class MainButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewMainButtonBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_main_button, this)
        binding = ViewMainButtonBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.MainButton, defStyleAttr, 0) {
            setDrawable(getDrawable(R.styleable.MainButton_src))
            setText(getString(R.styleable.MainButton_text))
            setTint(getColor(R.styleable.MainButton_color, Color.BLACK))
        }
    }

    fun setDrawable(drawable: Drawable?) = binding.image.setImageDrawable(drawable)

    fun setText(text: CharSequence?) = binding.text.setText(text)

    fun setTint(color: Int?) {
        binding.image.imageTintList = color?.let { ColorStateList.valueOf(it) }
        binding.text.setTextColor(color ?: Color.BLACK)
    }

    override fun setOnClickListener(l: OnClickListener?) = binding.container.setOnClickListener(l)
}
