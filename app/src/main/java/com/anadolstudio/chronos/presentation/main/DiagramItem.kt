package com.anadolstudio.chronos.presentation.main

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemLineDiagramBinding
import com.anadolstudio.chronos.view.diagram.LineProgressView
import com.anadolstudio.core.groupie.BaseGroupItem
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import org.joda.time.DateTime

class DiagramItem(
        private val data: Data,
        private val onClick: () -> Unit
) : BaseGroupItem<ItemLineDiagramBinding>(ID, R.layout.item_line_diagram) {

    companion object {
        const val ID = 1_000_100L
    }

    override fun initializeViewBinding(view: View): ItemLineDiagramBinding = ItemLineDiagramBinding.bind(view)

    override fun bind(binding: ItemLineDiagramBinding, item: BaseGroupItem<ItemLineDiagramBinding>) {
        if (item !is DiagramItem) return

        binding.cardView.scaleAnimationOnClick { onClick.invoke() }

        binding.diagram.setup(
                hours = item.data.hours,
                currentDate = item.data.currentDate,
                nextDateEnable = item.data.nextDateEnable,
                progressDataList = item.data.progressDataList,
                onNextDateClick = item.data.onNextDateClick,
                onPreviousDateClick = item.data.onPreviousDateClick,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DiagramItem

        if (data != other.data) return false

        return true
    }

    override fun hashCode(): Int = data.hashCode()

    data class Data(
            val hours: Float,
            val currentDate: DateTime,
            val progressDataList: List<LineProgressView.ProgressData>,
            val onNextDateClick: () -> Unit,
            val onPreviousDateClick: () -> Unit,
            val nextDateEnable: Boolean
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Data

            if (hours != other.hours) return false
            if (currentDate != other.currentDate) return false
            if (progressDataList != other.progressDataList) return false
            if (nextDateEnable != other.nextDateEnable) return false

            return true
        }

        override fun hashCode(): Int {
            var result = hours.hashCode()
            result = 31 * result + currentDate.hashCode()
            result = 31 * result + progressDataList.hashCode()
            result = 31 * result + nextDateEnable.hashCode()
            return result
        }
    }
}
