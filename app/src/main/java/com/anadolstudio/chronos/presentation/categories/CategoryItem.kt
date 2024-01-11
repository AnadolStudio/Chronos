package com.anadolstudio.chronos.presentation.categories

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemCategoryBinding
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.groupie.BaseGroupItem
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick

class CategoryItem(
        private val categoryUi: CategoryUi,
        private val onClick: (CategoryUi) -> Unit
) : BaseGroupItem<ItemCategoryBinding>(categoryUi.hashCode().toLong(), R.layout.item_category) {

    override fun initializeViewBinding(view: View): ItemCategoryBinding = ItemCategoryBinding.bind(view)

    override fun bind(binding: ItemCategoryBinding, item: BaseGroupItem<ItemCategoryBinding>) {
        if (item !is CategoryItem) return

        binding.cardView.scaleAnimationOnClick(action = { onClick.invoke(item.categoryUi) })
        binding.categoryVew.setup(item.categoryUi)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryItem

        if (categoryUi != other.categoryUi) return false

        return true
    }

    override fun hashCode(): Int = categoryUi.hashCode()

}
