package com.anadolstudio.chronos.view.button.base

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewBaseButtonBinding

open class BaseButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewBaseButtonBinding.inflate(LayoutInflater.from(context), this)
    private var state: State = State.ENABLE

    init {
        context.withStyledAttributes(attrs, R.styleable.BaseButton, defStyleAttr, defStyleRes) {
            initButtonText(this)
            changeState(isEnabled)
        }
    }

    private fun initButtonText(attrs: TypedArray) = with(attrs) {
        setTextColor(getColor(R.styleable.BaseButton_color, context.getColor(R.color.colorAccent)))
        setText(getText(R.styleable.BaseButton_text))
    }

    override fun setEnabled(enabled: Boolean) {
        changeState(enabled)
        super.setEnabled(enabled)
        binding.title.isEnabled = enabled
    }

    private fun changeState(enabled: Boolean) {
        state = if (enabled) State.ENABLE else State.DISABLE

        when (state) {
            State.ENABLE -> {
                binding.title.setTextColor(context.getColor(R.color.colorAccent))
                binding.cardView.setCardBackgroundColor(context.getColor(R.color.colorPrimary))
                binding.cardView.cardElevation = context.resources.getDimension(com.anadolstudio.core.R.dimen.elevation_normal)
            }

            State.DISABLE -> {
                binding.title.setTextColor(context.getColor(R.color.disableForeground))
                binding.cardView.setCardBackgroundColor(context.getColor(R.color.disableBackground))
                binding.cardView.cardElevation = 0F
            }
        }
    }

    fun setText(text: CharSequence?) {
        binding.title.text = text
    }

    fun setText(@StringRes textRes: Int) = setText(context.getString(textRes))

    fun setLoading(isLoading: Boolean) {
        super.setEnabled(!isLoading)
        binding.progressContainer.isVisible = isLoading
    }

    private fun setTextColorRes(@ColorRes colorRes: Int) = setTextColor(context.getColor(colorRes))

    private fun setTextColor(@ColorInt color: Int) {
        binding.title.setTextColor(color)
        binding.progressView.indeterminateTintList = ColorStateList.valueOf(color)
    }

    enum class State {
        ENABLE,
        DISABLE
    }
}
