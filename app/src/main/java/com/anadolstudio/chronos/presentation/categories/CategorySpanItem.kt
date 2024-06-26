package com.anadolstudio.chronos.presentation.categories

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemSpanCategoryBinding
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.ui.adapters.groupie.BaseGroupItem
import com.anadolstudio.utils.animation.AnimateUtil.scaleAnimationOnClick

class CategorySpanItem(
        private val categoryUi: CategoryUi,
        private val onClick: (CategoryUi) -> Unit
) : BaseGroupItem<ItemSpanCategoryBinding>(categoryUi.hashCode().toLong(), R.layout.item_span_category) {

    override fun initializeViewBinding(view: View): ItemSpanCategoryBinding = ItemSpanCategoryBinding.bind(view)

    override fun getSpanSize(spanCount: Int, position: Int): Int = 1

    override fun bind(binding: ItemSpanCategoryBinding, item: BaseGroupItem<ItemSpanCategoryBinding>) {
        if (item !is CategorySpanItem) return

        binding.cardView.scaleAnimationOnClick(action = { onClick.invoke(item.categoryUi) })
        binding.colorView.setBackgroundColor(item.categoryUi.color)
        binding.nameText.text = item.categoryUi.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategorySpanItem

        if (categoryUi != other.categoryUi) return false

        return true
    }

    override fun hashCode(): Int = categoryUi.hashCode()

}
