package com.anadolstudio.chronos.presentation.track

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemLastTrackBinding
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.core.groupie.BaseGroupItem
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick

class LastTrackItem(
        private val categoryUi: CategoryUi,
        private val onClick: (CategoryUi) -> Unit
) : BaseGroupItem<ItemLastTrackBinding>(categoryUi.hashCode().toLong(), R.layout.item_last_track) {

    override fun initializeViewBinding(view: View): ItemLastTrackBinding = ItemLastTrackBinding.bind(view)

    override fun bind(binding: ItemLastTrackBinding, item: BaseGroupItem<ItemLastTrackBinding>) {
        if (item !is LastTrackItem) return

        binding.chipVew.scaleAnimationOnClick(action = { onClick.invoke(item.categoryUi) })
        binding.chipVew.setText(item.categoryUi.name)
        binding.chipVew.setBackgroundColor(item.categoryUi.color)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LastTrackItem

        if (categoryUi != other.categoryUi) return false

        return true
    }

    override fun hashCode(): Int = categoryUi.hashCode()

}
