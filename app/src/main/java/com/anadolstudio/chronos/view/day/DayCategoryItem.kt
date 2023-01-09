package com.anadolstudio.chronos.view.day

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewDayCategoryBinding
import com.anadolstudio.domain.models.days.DaysCategoryModel

typealias CategoryItemClickListener = (category: String, subcategory: String, subcategoryObject: String?) -> Unit

class DayCategoryItem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding: ViewDayCategoryBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_day_category, this)
        binding = ViewDayCategoryBinding.bind(view)
    }

    private var onItemClickListener: CategoryItemClickListener? = null

    fun setCategory(data: DaysCategoryModel.Category) {
        with(binding) {
            category.setTitle(data.title)
            category.setTime(data.time)
            categoryTitleContainer.setBackgroundColor(data.color)

            subcategoryContainer.isVisible = data.subcategoriesList.isNotEmpty()
            subcategoryContainer.removeAllViews()

            data.subcategoriesList.forEach { subcategory ->
                val view = DaySubcategoryItemView(context).apply {
                    layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    setSubcategory(subcategory)
                    setOnSubcategoryClickListener { subcategory, subcategoryObject ->
                        onItemClickListener?.invoke(data.title, subcategory,subcategoryObject)
                    }
                }

                subcategoryContainer.addView(view)
            }
        }
    }

    fun setOnSubcategoryClickListener(listener: CategoryItemClickListener) {
        onItemClickListener = listener
    }
}
