package com.anadolstudio.chronos.view.diagram

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewCircleDiagramBinding
import com.anadolstudio.chronos.util.toSimpleDateFormat
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
        binding.root.setPadding(context.resources.getDimension(com.anadolstudio.core.R.dimen.padding_medium).toInt())
    }

    fun setup(
            hours: Float,
            startDate: DateTime,
            endDate: DateTime,
            progressDataList: List<ProgressData>,
            onFromDateClick: () -> Unit,
            onToDateClick: () -> Unit,
    ) {
        val totalMillis = endDate.withTimeAtStartOfDay().millis - startDate.withTimeAtStartOfDay().millis
        val totalHours = TimeUnit.MILLISECONDS.toHours(totalMillis)

        binding.fromDateText.text = context.getString(R.string.from_date, startDate.toSimpleDateFormat())
        binding.toDateText.text = context.getString(R.string.to_date, endDate.toSimpleDateFormat())
        binding.fromDateContainer.setOnClickListener { onFromDateClick.invoke() }
        binding.toDateContainer.setOnClickListener { onToDateClick.invoke() }
        binding.progress.setup(progressDataList)
        val hoursFormat = String.format("%.1f", hours)
        binding.timeText.text = context.getString(R.string.global_diagram_time_free_format, hoursFormat, totalHours.toString())
    }

}
