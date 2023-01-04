package com.anadolstudio.chronos.view.day

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewDaySubcategoryItemBinding
import com.anadolstudio.chronos.ui.days.model.Subcategory
import com.anadolstudio.core.common_util.dpToPx

class DaySubcategoryItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private companion object {
        val PADDING_VERTICAL = 5.dpToPx()
    }

    private val binding: ViewDaySubcategoryItemBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_day_subcategory_item, this)
        binding = ViewDaySubcategoryItemBinding.bind(view)
    }

    fun setSubcategory(data: Subcategory) {
        with(binding) {
            subcategory.setTitle(data.title)
            subcategory.setTime(data.time)

            objectsGroup.isVisible = data.objectsList.isNotEmpty()

            subcategoryObjectContainer.removeAllViews()
            data.objectsList.forEach { subcategoryObject ->
                val view = DayTaskItemView(context).apply {
                    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    setPadding(0, PADDING_VERTICAL, 0, PADDING_VERTICAL)
                    setTitle(context.getString(R.string.global_text_item_list, subcategoryObject.title))
                    setTime(subcategoryObject.time)
                    setColor(context.getColor(R.color.gray))
                }

                subcategoryObjectContainer.addView(view)
            }
        }
    }
}
