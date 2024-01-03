package com.anadolstudio.chronos.view.button.feature

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewFeatureButtonBinding
import com.anadolstudio.chronos.view.function.Imageble
import com.anadolstudio.chronos.view.function.Textable
import com.anadolstudio.core.util.common.dpToPx

class FeatureButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), Imageble, Textable {

    private companion object {
        val DEFAULT_INNER_PADDING = 16.dpToPx()
    }

    private val binding: ViewFeatureButtonBinding = ViewFeatureButtonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.view_feature_button, this, true)
    )

    init {
        initializeAttributes(attrs)
    }

    private var state: State = State.IMAGE

    private fun initializeAttributes(attrs: AttributeSet?) {
        if (attrs == null) return
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.FeatureButton)

        binding.apply {
            typeArray.getDrawable(R.styleable.FeatureButton_src).let(::setDrawable)
            val padding = typeArray.getDimensionPixelSize(R.styleable.FeatureButton_innerPadding, DEFAULT_INNER_PADDING)
            imageView.setPadding(padding)

            typeArray.getString(R.styleable.FeatureButton_text).let(::setText)
            typeArray.getInt(R.styleable.FeatureButton_state, State.IMAGE.ordinal).let(::setState)
        }

        typeArray.recycle()
    }

    override fun setDrawable(drawable: Drawable?) = binding.imageView.setImageDrawable(drawable)

    override fun setDrawable(drawableRes: Int) = setDrawable(ContextCompat.getDrawable(context, drawableRes))

    override fun setText(text: String?) = binding.textView.setText(text)

    override fun setText(textRes: Int) = binding.textView.setText(textRes)

    private fun setState(ordinal: Int) = setState(State.values()[ordinal])

    fun setState(state: State) {
        this.state = state
        binding.imageView.isVisible = state == State.IMAGE
        binding.textView.isVisible = state == State.TEXT
    }

    enum class State {
        IMAGE,
        TEXT
    }
}
