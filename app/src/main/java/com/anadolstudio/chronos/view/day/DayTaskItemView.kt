package com.anadolstudio.chronos.view.day

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewDayTaskItemBinding

class DayTaskItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewDayTaskItemBinding

    init {
        val view  = LayoutInflater.from(context).inflate(R.layout.view_day_task_item, this)
        binding = ViewDayTaskItemBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.DayTaskItemView, defStyleAttr, 0) {
            setTitle(getString(R.styleable.DayTaskItemView_title).orEmpty())
            setTime(getString(R.styleable.DayTaskItemView_time).orEmpty())
            setColor(getColor(R.styleable.DayTaskItemView_color, context.getColor(R.color.black)))
        }
    }

    fun setTitle(text: String) {
        binding.titleText.text = text
    }

    fun setTime(text: String) {
        binding.timeText.text = text
    }

    fun setColor(color: Int) {
        binding.titleText.setTextColor(color)
        binding.timeText.setTextColor(color)
    }
}
