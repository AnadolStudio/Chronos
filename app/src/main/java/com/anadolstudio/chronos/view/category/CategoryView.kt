package com.anadolstudio.chronos.view.category

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.anadolstudio.chronos.databinding.ViewCategoryBinding
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.util.common_extention.setLimitText

class CategoryView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private companion object {
        const val LIMIT = 25
    }

    private val binding = ViewCategoryBinding.inflate(LayoutInflater.from(context), this)

    fun setup(categoryUi: CategoryUi) {
        binding.colorView.background = ColorDrawable(categoryUi.color)
        binding.parent.setLimitText(categoryUi.parentName, LIMIT)
        binding.nameText.setLimitText(categoryUi.name, LIMIT)
        binding.parentGroup.isVisible = !categoryUi.isMainCategory
        binding.parentsStub.isVisible = !categoryUi.isRootCategory && !categoryUi.isMainCategory
    }
}
