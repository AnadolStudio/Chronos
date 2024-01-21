package com.anadolstudio.chronos.view.track

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import com.anadolstudio.chronos.databinding.ViewTreeTrackBinding
import com.anadolstudio.chronos.presentation.main.model.TrackChildUi
import com.anadolstudio.chronos.presentation.main.model.TrackRootUi
import com.anadolstudio.chronos.util.toTrackTime

class TrackTreeView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewTreeTrackBinding.inflate(LayoutInflater.from(context), this)

    fun setup(trackRootUi: TrackRootUi, onChildClick: ((TrackChildUi) -> Unit)? = null) = with(binding) {
        nameText.text = trackRootUi.name
        timeText.text = trackRootUi.time.toTrackTime(context)
        headerBackground.setBackgroundColor(trackRootUi.color)

        childContainer.removeAllViews()
        trackRootUi.notEmptyChildren.forEach {
            val child = TrackTreeItemView(context)
            child.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            child.setup(it, onChildClick)

            childContainer.addView(child)
        }
    }

}
