package com.anadolstudio.chronos.presentation.main

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemStopWatcherBinding
import com.anadolstudio.core.groupie.BaseGroupItem
import com.anadolstudio.core.util.common.throttleClick
import com.anadolstudio.core.util.common_extention.getCompatDrawable
import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData

class StopWatcherItem(
        private val data: StopWatcherData,
        private val time: Time?,
        private val onClick: () -> Unit,
        private val onStopWatcherToggleClick: () -> Unit
) : BaseGroupItem<ItemStopWatcherBinding>(ID, R.layout.item_stop_watcher) {

    companion object {
        const val ID = 1_000_101L
    }

    override fun initializeViewBinding(view: View): ItemStopWatcherBinding = ItemStopWatcherBinding.bind(view)

    override fun bind(binding: ItemStopWatcherBinding, item: BaseGroupItem<ItemStopWatcherBinding>) {
        if (item !is StopWatcherItem) return

        binding.root.scaleAnimationOnClick(action = { item.onClick.invoke() })
        binding.actionIcon.throttleClick { onStopWatcherToggleClick.invoke() }

        val context = binding.root.context

        val drawable = when (item.data.state) {
            StopWatcherData.State.IN_PROGRESS -> R.drawable.ic_pause
            StopWatcherData.State.RESULT,
            StopWatcherData.State.DEFAULT -> R.drawable.ic_play
        }

        binding.actionIcon.setImageDrawable(context.getCompatDrawable(drawable))

        val text = when (item.data.state) {
            StopWatcherData.State.IN_PROGRESS,
            StopWatcherData.State.RESULT -> item.time
                    ?.run {
                        context.getString(
                                R.string.global_full_time_text,
                                hoursString,
                                minutesString,
                                secondsString
                        )
                    }
                    ?: context.getText(R.string.stop_watcher)

            StopWatcherData.State.DEFAULT -> context.getText(R.string.stop_watcher)
        }
        binding.timeText.text = text

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StopWatcherItem

        if (data != other.data) return false
        if (time != other.time) return false

        return true
    }

    override fun hashCode(): Int = data.hashCode() + time.hashCode()

}
