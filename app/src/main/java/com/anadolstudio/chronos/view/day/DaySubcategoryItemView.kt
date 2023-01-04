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

typealias SubcategoryClickListener = (subcategory: String, subcategoryObject: String?) -> Unit

class DaySubcategoryItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private companion object {
        val PADDING_VERTICAL = 5.dpToPx()
        val PADDING_START = 16.dpToPx()
    }

    private val binding: ViewDaySubcategoryItemBinding
    private var onSubcategoryClickListener: SubcategoryClickListener? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_day_subcategory_item, this)
        binding = ViewDaySubcategoryItemBinding.bind(view)
    }

    fun setSubcategory(data: Subcategory) {
        with(binding) {
            subcategory.setTitle(text = data.title, isSubItem = true)
            subcategory.setTime(data.time)
            subcategory.onSubcategoryClickListener(subcategory = data.title)

            objectsGroup.isVisible = data.objectsList.isNotEmpty()
            subcategoryObjectContainer.removeAllViews()

            data.objectsList.forEach { subcategoryObject ->
                val view = DayTaskItemView(context).apply {
                    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    setPadding(PADDING_START, PADDING_VERTICAL, 0, PADDING_VERTICAL)
                    setTitle(text = subcategoryObject.title, isSubItem = true)
                    setTime(subcategoryObject.time)
                    setColor(context.getColor(R.color.gray))
                    onSubcategoryClickListener(subcategory = data.title, withObjectName = true)
                }

                subcategoryObjectContainer.addView(view)
            }
        }
    }

    private fun DayTaskItemView.onSubcategoryClickListener(subcategory: String, withObjectName: Boolean = false) =
            setOnTaskItemClickListener { objectName ->
                onSubcategoryClickListener?.invoke(subcategory, if (withObjectName) objectName else null)
            }

    fun setOnSubcategoryClickListener(listener: SubcategoryClickListener) {
        onSubcategoryClickListener = listener
    }

}
