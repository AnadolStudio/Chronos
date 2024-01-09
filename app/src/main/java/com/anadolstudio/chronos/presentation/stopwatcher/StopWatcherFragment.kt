package com.anadolstudio.chronos.presentation.stopwatcher

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentStopWatcherBinding
import com.anadolstudio.chronos.presentation.track.TrackBottom
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData

class StopWatcherFragment :
    BaseContentFragment<StopWatcherState, StopWatcherViewModel, StopWatcherController>(R.layout.fragment_stop_watcher) {

    override val viewStateDelegate: ViewStateDelegate = ViewStateDelegate()

    private val binding by viewBinding { FragmentStopWatcherBinding.bind(it) }

    override fun createViewModelLazy() = viewModels<StopWatcherViewModel> { viewModelFactory }

    override fun initView() = with(binding) {
        binding.toolbar.setBackClickListener(controller::onBackClicked)
        stopWatcher.addListeners(
            onAddButtonAction = { controller.onAddButtonClicked() },
            onRemoveButtonAction = { controller.onRemoveButtonClicked() }
        )
        stopWatcherToggle.scaleAnimationOnClick { controller.onStopWatcherToggleClicked() }
        initFragmentResultListeners(TrackBottom.TAG)
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        TrackBottom.TAG -> controller.onTimeTracked()
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun render(state: StopWatcherState) {
        renderStopWatcher(state.stopWatcherData)
        binding.stopWatcherToggle.setLoading(state.isLoading)
    }

    private fun renderStopWatcher(stopWatcherData: StopWatcherData) = with(stopWatcherData) {
        binding.stopWatcher.setup(this)

        val toggleTextRes = when (state) {
            StopWatcherData.State.DEFAULT -> R.string.global_start
            StopWatcherData.State.IN_PROGRESS -> R.string.global_stop
            StopWatcherData.State.RESULT -> R.string.global_resume
        }

        binding.stopWatcherToggle.setText(toggleTextRes)
    }

}
