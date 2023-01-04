package com.anadolstudio.chronos.view.day

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewDayTaskItemBinding
import com.anadolstudio.core.common_util.throttleClick

class DayTaskItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewDayTaskItemBinding
    private var onTaskItemClickListener: ((String) -> Unit)? = null
    private var title: String? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_day_task_item, this)
        binding = ViewDayTaskItemBinding.bind(view)

        context.withStyledAttributes(attrs, R.styleable.DayTaskItemView, defStyleAttr, 0) {
            setTitle(getString(R.styleable.DayTaskItemView_title).orEmpty())
            setTime(getString(R.styleable.DayTaskItemView_time).orEmpty())
            setColor(getColor(R.styleable.DayTaskItemView_color, context.getColor(R.color.black)))
        }

        val outValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        setBackgroundResource(outValue.resourceId)
    }

    fun setTitle(text: String, isSubItem: Boolean = false) {
        title = text
        binding.titleText.text = if (isSubItem) context.getString(R.string.global_text_item_list, text) else text
    }

    fun setTime(text: String) {
        binding.timeText.text = text
    }

    fun setColor(color: Int) {
        binding.titleText.setTextColor(color)
        binding.timeText.setTextColor(color)
    }

    fun setOnTaskItemClickListener(listener: (String) -> Unit) {
        onTaskItemClickListener = listener

        throttleClick {
            onTaskItemClickListener?.invoke(title.orEmpty())
        }
    }
}
