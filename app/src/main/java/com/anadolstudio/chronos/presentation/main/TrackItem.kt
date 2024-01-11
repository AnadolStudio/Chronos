package com.anadolstudio.chronos.presentation.main

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemTrackBinding
import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.core.groupie.BaseGroupItem
import com.anadolstudio.core.util.common_extention.getCompatDrawable
import com.anadolstudio.core.util.common_extention.vibrationLongClickListener

class TrackItem(
        private val trackRootUi: TrackRootUi,
        private val onClick: ((TrackRootUi) -> Unit)? = null,
        private val onChildClick: ((TrackChildUi) -> Unit)? = null,
) : BaseGroupItem<ItemTrackBinding>(trackRootUi.id.hashCode().toLong(), R.layout.item_track) {

    override fun initializeViewBinding(view: View): ItemTrackBinding = ItemTrackBinding.bind(view)

    override fun bind(binding: ItemTrackBinding, item: BaseGroupItem<ItemTrackBinding>) {
        if (item !is TrackItem) return

        val context = binding.root.context

        if (onClick == null) {
            binding.cardView.foreground = null
            binding.cardView.setOnLongClickListener(null)
        } else {
            binding.cardView.foreground = context.getCompatDrawable(com.anadolstudio.core.R.drawable.item_ripple_rectangle_8)
            binding.cardView.vibrationLongClickListener { onClick.invoke(item.trackRootUi) }
        }

        binding.trackRootVew.setup(item.trackRootUi, item.onChildClick)
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
