package com.anadolstudio.chronos.view.stop_watcher

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isInvisible
import androidx.transition.TransitionManager
import com.anadolstudio.chronos.databinding.ViewStopWatcherBinding
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData.State
import com.anadolstudio.utils.animation.AnimateUtil.scaleAnimationOnClick

class StopWatcherView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewStopWatcherBinding.inflate(LayoutInflater.from(context), this)

    fun setup(data: StopWatcherData) {
        binding.apply {

            binding.clockFace.setup(data)

            val delta = data.deltaTime?.minutes ?: 0
            addButton.isEnabled = data.state == State.RESULT && delta > 0

            TransitionManager.beginDelayedTransition(binding.removeButton)
            TransitionManager.beginDelayedTransition(binding.addButton)

            addButton.isInvisible = data.state != State.RESULT
            removeButton.isInvisible = data.state != State.RESULT
        }
    }

    fun addListeners(onAddButtonAction: () -> Unit, onRemoveButtonAction: () -> Unit) {
        binding.apply {
            addButton.scaleAnimationOnClick { onAddButtonAction.invoke() }
            removeButton.scaleAnimationOnClick { onRemoveButtonAction.invoke() }
        }
    }

}
