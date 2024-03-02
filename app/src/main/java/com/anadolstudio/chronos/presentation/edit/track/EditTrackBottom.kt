package com.anadolstudio.chronos.presentation.edit.track

import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.presentation.track.base.BaseTrackBottom
import com.anadolstudio.chronos.presentation.track.base.BaseTrackController
import com.anadolstudio.utils.util.extentions.setFragmentResult
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent

class EditTrackBottom : BaseTrackBottom<EditTrackState, EditTrackViewModel, BaseTrackController>() {

    private companion object {
        const val RENDER_BUTTON_ACTION = "RENDER_BUTTON_ACTION"
    }

    private val args: EditTrackBottomArgs by navArgs()

    override fun initView() {
        super.initView()
        binding.nameText.setSupportHint(getString(R.string.prev_name, args.data.trackChildUi.name))
    }

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .editTrackViewModelFactory()
                .create(args.data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is EditTrackBottomEvents.Result -> setFragmentResult(args.data.requestKey)
        else -> super.handleEvent(event)
    }

    override fun render(state: EditTrackState) {
        super.render(state)
        renderButtonName(state.buttonState)
        binding.applyButton.isEnabled = state.buttonEnable
    }

    private fun renderButtonName(buttonState: ButtonState) = buttonState.render(RENDER_BUTTON_ACTION) {
        when (buttonState) {
            ButtonState.REMOVE -> {
                binding.applyButton.setText(R.string.global_remove)
                binding.applyButton.setTextColor(requireContext().getColor(R.color.colorText))
                binding.applyButton.setEnableColor(requireContext().getColor(R.color.colorRemove))
            }

            ButtonState.TRANSFER -> {
                binding.applyButton.setText(R.string.global_transfer)
                binding.applyButton.setTextColor(requireContext().getColor(R.color.colorAccent))
                binding.applyButton.setEnableColor(requireContext().getColor(R.color.colorPrimary))
            }

            ButtonState.UPDATE -> {
                binding.applyButton.setText(R.string.global_change)
                binding.applyButton.setTextColor(requireContext().getColor(R.color.colorAccent))
                binding.applyButton.setEnableColor(requireContext().getColor(R.color.colorPrimary))
            }
        }
    }

}
