package com.anadolstudio.chronos.view.button.base

import android.annotation.SuppressLint
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
import com.anadolstudio.core.util.common_extention.setDimensMargins

open class BaseButton @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewBaseButtonBinding.inflate(LayoutInflater.from(context), this)
    private var state: State = State.ENABLE
    private var enableColor: Int = context.getColor(R.color.colorPrimary)
    private var enableTextColor: Int = context.getColor(R.color.colorAccent)

    init {
        context.withStyledAttributes(attrs, R.styleable.BaseButton, defStyleAttr, defStyleRes) {
            initButtonText(this)
            enableColor = getColor(R.styleable.BaseButton_backgroundColor, context.getColor(R.color.colorPrimary))

            val paddingTop = getDimension(
                    R.styleable.BaseButton_paddingTop,
                    context.resources.getDimension(com.anadolstudio.core.R.dimen.padding_main)
            )
            val paddingBottom = getDimension(
                    R.styleable.BaseButton_paddingBottom,
                    context.resources.getDimension(com.anadolstudio.core.R.dimen.padding_main)
            )

            binding.cardView.setDimensMargins(top = paddingTop.toInt(), bottom = paddingBottom.toInt())
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
                binding.title.setTextColor(enableTextColor)
                binding.cardView.setCardBackgroundColor(enableColor)
                binding.cardView.cardElevation = context.resources.getDimension(com.anadolstudio.core.R.dimen.elevation_normal)
            }

            State.DISABLE -> {
                binding.title.setTextColor(context.getColor(R.color.disableForeground))
                binding.cardView.setCardBackgroundColor(context.getColor(R.color.disableBackground))
                binding.cardView.cardElevation = 0F
            }
        }
    }

    private fun invalidateState() = changeState(isEnabled)

    fun setText(text: CharSequence?) {
        binding.title.text = text
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setOnTouchListener(l: OnTouchListener?) {
        binding.cardView.setOnTouchListener(l)
    }

    fun setText(@StringRes textRes: Int) = setText(context.getString(textRes))

    fun setLoading(isLoading: Boolean) {
        super.setEnabled(!isLoading)
        binding.progressContainer.isVisible = isLoading
    }

    fun setTextColorRes(@ColorRes colorRes: Int) = setTextColor(context.getColor(colorRes))

    fun setTextColor(@ColorInt color: Int) {
        enableTextColor = color
        binding.title.setTextColor(color)
        binding.progressView.indeterminateTintList = ColorStateList.valueOf(color)
    }

    fun setEnableColor(color: Int) {
        enableColor = color
        invalidateState()
    }

    enum class State {
        ENABLE,
        DISABLE
    }
}
