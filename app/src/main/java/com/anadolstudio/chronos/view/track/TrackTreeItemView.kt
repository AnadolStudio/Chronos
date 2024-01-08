package com.anadolstudio.chronos.view.track

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.anadolstudio.chronos.databinding.ViewTreeTrackItemBinding
import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.chronos.util.toTrackTime

class TrackTreeItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewTreeTrackItemBinding.inflate(LayoutInflater.from(context), this)

    fun setup(trackChildUi: TrackChildUi) = with(binding) {
        nameText.text = trackChildUi.name
        timeText.text = trackChildUi.time.toTrackTime(context)

        inflateChildren(trackChildUi)
    }

    private fun ViewTreeTrackItemBinding.inflateChildren(trackChildUi: TrackChildUi) {
        trackChildUi.children.forEach {
            val child = TrackTreeItemView(context)
            child.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            child.setup(it)

            childContainer.addView(child)
        }
    }
}
