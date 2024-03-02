package com.anadolstudio.chronos.view.diagram

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewCircleDiagramBinding
import com.anadolstudio.chronos.util.shortFormat
import com.anadolstudio.chronos.util.toWeekDayDateFormat
import com.anadolstudio.utils.R.*
import org.joda.time.DateTime
import java.util.concurrent.TimeUnit

class CircleDiagramView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewCircleDiagramBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.root.setPadding(context.resources.getDimension(dimen.padding_medium).toInt())
    }

    fun setup(
            hours: Float,
            totalMinutes: Long,
            startDate: DateTime,
            endDate: DateTime,
            progressDataList: List<ProgressData>,
            onPeriodClick: () -> Unit,
    ) {
        val totalHours = TimeUnit.MINUTES.toHours(totalMinutes)

        binding.fromDateText.text = context.getString(R.string.from_date, startDate.toWeekDayDateFormat())
        binding.toDateText.text = context.getString(R.string.to_date, endDate.toWeekDayDateFormat())
        binding.periodContainer.setOnClickListener { onPeriodClick.invoke() }
        binding.progress.setup(progressDataList)
        binding.timeText.text = context.getString(
                R.string.global_diagram_time_free_format,
                hours.shortFormat(),
                totalHours.toString()
        )
    }

}
