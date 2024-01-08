package com.anadolstudio.chronos.presentation.main

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemTrackBinding
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.core.groupie.BaseGroupItem
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick

class TrackItem(
        private val trackRootUi: TrackRootUi,
        private val onClick: (TrackRootUi) -> Unit
) : BaseGroupItem<ItemTrackBinding>(trackRootUi.id.hashCode().toLong(), R.layout.item_track) {

    override fun initializeViewBinding(view: View): ItemTrackBinding = ItemTrackBinding.bind(view)

    override fun bind(binding: ItemTrackBinding, item: BaseGroupItem<ItemTrackBinding>) {
        if (item !is TrackItem) return

        binding.cardView.scaleAnimationOnClick(action = { onClick.invoke(item.trackRootUi) })
        binding.trackRootVew.setup(item.trackRootUi)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrackItem

        if (trackRootUi != other.trackRootUi) return false

        return true
    }

    override fun hashCode(): Int = trackRootUi.hashCode()

}
