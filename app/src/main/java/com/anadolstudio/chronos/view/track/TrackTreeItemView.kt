package com.anadolstudio.chronos.view.track

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.ViewTreeTrackItemBinding
import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.chronos.util.toTrackTime
import com.anadolstudio.utils.util.common.throttleClick
import com.anadolstudio.utils.util.extentions.getCompatDrawable

class TrackTreeItemView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewTreeTrackItemBinding.inflate(LayoutInflater.from(context), this)

    fun setup(trackChildUi: TrackChildUi, onChildClick: ((TrackChildUi) -> Unit)?) = with(binding) {
        val isChild = trackChildUi.notEmptyChildren.isEmpty()
        val needClick = onChildClick != null
                && isChild
                && trackChildUi.time.totalMinutes > 0

        if (needClick) {
            textContainer.throttleClick { onChildClick?.invoke(trackChildUi) }
        }

        if (needClick || onChildClick == null) {
            nameText.setTextColor(context.getColor(R.color.colorAccent))
            timeText.setTextColor(context.getColor(R.color.colorAccent))
        }else {
            nameText.setTextColor(context.getColor(R.color.disableForeground))
            timeText.setTextColor(context.getColor(R.color.disableForeground))
        }

        textContainer.background = onChildClick?.let {
            context.getCompatDrawable(com.anadolstudio.view.R.drawable.item_ripple_rectangle_8)
        }
        nameText.text = trackChildUi.name
        timeText.text = trackChildUi.time.toTrackTime(context)

        val typeFace = if (isChild) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        nameText.typeface = typeFace
        timeText.typeface = typeFace
        inflateChildren(trackChildUi, onChildClick)
    }

    private fun ViewTreeTrackItemBinding.inflateChildren(
            trackChildUi: TrackChildUi,
            onChildClick: ((TrackChildUi) -> Unit)?
    ) {
        trackChildUi.notEmptyChildren.forEach {
            val child = TrackTreeItemView(context)
            child.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            child.setup(it, onChildClick)

            childContainer.addView(child)
        }
    }
}
