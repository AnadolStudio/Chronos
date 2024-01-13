package com.anadolstudio.chronos.presentation.statistic

import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ItemCircleDiagramBinding
import com.anadolstudio.chronos.view.diagram.ProgressData
import com.anadolstudio.core.groupie.BaseGroupItem
import org.joda.time.DateTime

class DiagramItem(
        private val data: Data,
) : BaseGroupItem<ItemCircleDiagramBinding>(ID, R.layout.item_circle_diagram) {

    companion object {
        const val ID = 1_000_100L
    }

    override fun initializeViewBinding(view: View): ItemCircleDiagramBinding = ItemCircleDiagramBinding.bind(view)

    override fun bind(binding: ItemCircleDiagramBinding, item: BaseGroupItem<ItemCircleDiagramBinding>) {
        if (item !is DiagramItem) return

        binding.diagram.setup(
                hours = item.data.hours,
                startDate = item.data.fromDate,
                endDate = item.data.toDate,
                onFromDateClick = item.data.onFromDateClick,
                onToDateClick = item.data.onToDateClick,
                totalMinutes = item.data.totalMinutes,
                progressDataList = item.data.progressDataList,
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
            val totalMinutes: Long,
            val fromDate: DateTime,
            val toDate: DateTime,
            val progressDataList: List<ProgressData>,
            val onFromDateClick: () -> Unit,
            val onToDateClick: () -> Unit,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Data

            if (hours != other.hours) return false
            if (fromDate != other.fromDate) return false
            if (toDate != other.toDate) return false
            if (progressDataList != other.progressDataList) return false
            if (totalMinutes != other.totalMinutes) return false

            return true
        }

        override fun hashCode(): Int {
            var result = hours.hashCode()
            result = 31 * result + fromDate.hashCode()
            result = 31 * result + toDate.hashCode()
            result = 31 * result + progressDataList.hashCode()
            result = 31 * result + totalMinutes.hashCode()
            return result
        }
    }
}
