package com.anadolstudio.chronos.view.diagram

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewLineDiagramBinding
import com.anadolstudio.chronos.util.minusDay
import com.anadolstudio.chronos.util.plusDay
import com.anadolstudio.chronos.util.shortFormat
import com.anadolstudio.chronos.util.toSimpleDateFormat
import com.anadolstudio.chronos.util.toWeekDayDateFormat
import com.anadolstudio.utils.R.*
import org.joda.time.DateTime

class LineDiagramView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewLineDiagramBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.root.setPadding(context.resources.getDimension(dimen.padding_medium).toInt())
    }

    fun setup(
            hours: Float,
            currentDate: DateTime,
            progressDataList: List<ProgressData>,
            onNextDateClick: () -> Unit,
            onPreviousDateClick: () -> Unit,
            nextDateEnable: Boolean
    ) {
        binding.dateText.text = currentDate.toWeekDayDateFormat()
        binding.previousDateText.text = currentDate.minusDay().toSimpleDateFormat()
        binding.nextDateText.text = currentDate.plusDay().toSimpleDateFormat()
        binding.previousDateContainer.setOnClickListener { onPreviousDateClick.invoke() }
        binding.nextDateContainer.setOnClickListener { onNextDateClick.invoke() }
        binding.nextDateContainer.isVisible = nextDateEnable
        binding.progress.setup(progressDataList)
        binding.timeText.text = context.getString(R.string.global_diagram_time_format, hours.shortFormat())
    }

}
