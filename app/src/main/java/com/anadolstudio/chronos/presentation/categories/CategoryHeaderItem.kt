package com.anadolstudio.chronos.presentation.categories

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemHeaderCategoryBinding
import com.anadolstudio.ui.adapters.groupie.BaseGroupItem

class CategoryHeaderItem(
        private val name: String,
) : BaseGroupItem<ItemHeaderCategoryBinding>(name.hashCode().toLong(), R.layout.item_header_category) {

    override fun initializeViewBinding(view: View): ItemHeaderCategoryBinding = ItemHeaderCategoryBinding.bind(view)

    override fun getSpanSize(spanCount: Int, position: Int): Int = spanCount

    override fun bind(binding: ItemHeaderCategoryBinding, item: BaseGroupItem<ItemHeaderCategoryBinding>) {
        if (item !is CategoryHeaderItem) return

        binding.nameText.text = item.name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoryHeaderItem

        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()

}
