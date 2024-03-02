package com.anadolstudio.chronos.presentation.main

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemTrackStubBinding
import com.anadolstudio.ui.adapters.groupie.BaseGroupItem

class TrackStubItem : BaseGroupItem<ItemTrackStubBinding>(ID.toLong(), R.layout.item_track_stub) {

    private companion object {
        const val ID = 1_000_001
    }

    override fun initializeViewBinding(view: View): ItemTrackStubBinding = ItemTrackStubBinding.bind(view)

    override fun bind(binding: ItemTrackStubBinding, item: BaseGroupItem<ItemTrackStubBinding>) = Unit

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        return true
    }

    override fun hashCode(): Int = ID.hashCode()

}
