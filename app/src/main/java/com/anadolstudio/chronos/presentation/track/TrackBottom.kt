package com.anadolstudio.chronos.presentation.track

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.presentation.create.CreateBottom
import com.anadolstudio.chronos.presentation.track.base.BaseTrackBottom
import com.anadolstudio.utils.util.extentions.requireParcelable
import com.anadolstudio.utils.util.extentions.setFragmentResult
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent

open class TrackBottom : BaseTrackBottom<TrackState, TrackViewModel, TrackController>() {

    companion object {
        const val TAG = "TrackBottom"
        private const val RENDER_BUTTON_ENABLE = "RENDER_BUTTON_ENABLE"
        private const val RENDER_BUTTON_NAME = "RENDER_BUTTON_NAME"
    }

    private val args: TrackBottomArgs by navArgs()

    override fun getDialogTag(): String = TAG

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .trackViewModelFactory()
                .create(args.data)
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        CreateBottom.TAG -> controller.onCategoryCreated(requireParcelable(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is TrackBottomEvents.Result -> setFragmentResult(getDialogTag())
        else -> super.handleEvent(event)
    }

    override fun render(state: TrackState) = with(state) {
        super.render(state)
        renderApplyButton(state.applyButtonState.isEnable, state.applyButtonState.hasSelectedCategory)
    }

    private fun renderApplyButton(buttonEnable: Boolean, hasSelectedCategory: Boolean) = buttonEnable.render(
            RENDER_BUTTON_ENABLE,
            RENDER_BUTTON_NAME to hasSelectedCategory
    ) {
        onRenderApplyButton(buttonEnable, hasSelectedCategory)
    }

    protected open fun onRenderApplyButton(buttonEnable: Boolean, hasSelectedCategory: Boolean) {
        val textRes = when (hasSelectedCategory) {
            true -> R.string.global_add
            false -> R.string.global_create
        }
        binding.applyButton.setText(textRes)
        binding.applyButton.isEnabled = buttonEnable
    }
}
