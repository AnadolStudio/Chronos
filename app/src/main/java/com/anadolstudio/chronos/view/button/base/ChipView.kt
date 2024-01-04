package com.anadolstudio.chronos.view.button.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.anadolstudio.chronos.databinding.ViewChipBinding
import com.anadolstudio.chronos.view.function.Textable

class ChipView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), Textable {

    private val binding = ViewChipBinding.inflate(LayoutInflater.from(context), this)

    override fun setBackgroundColor(color: Int) = binding.cardView.setCardBackgroundColor(color)

    override fun setText(text: String?) = binding.text.setText(text)

    override fun setText(textRes: Int) = binding.text.setText(textRes)

}
