package com.anadolstudio.chronos.view.button.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewChipBinding
import com.anadolstudio.chronos.util.ellipsize
import com.anadolstudio.chronos.view.function.Textable

class ChipView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), Textable {

    private companion object {
        const val MAX_LENGTH = 20
    }

    private val binding = ViewChipBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.withStyledAttributes(attrs, R.styleable.ChipView, defStyleAttr) {
            getString(R.styleable.ChipView_text)?.let(this@ChipView::setText)
            val backgroundColor = getColor(
                    R.styleable.BaseButton_backgroundColor, context.getColor(R.color.colorPrimary)
            )
            setBackgroundColor(backgroundColor)

            val textColor = getColor(
                    R.styleable.BaseButton_color, context.getColor(R.color.black)
            )
            binding.text.setTextColor(textColor)
        }
    }

    override fun setBackgroundColor(color: Int) = binding.cardView.setCardBackgroundColor(color)

    override fun setText(text: String?) = binding.text.setText(text?.ellipsize(MAX_LENGTH))

    override fun setText(textRes: Int) = setText(context.getString(textRes))

}
