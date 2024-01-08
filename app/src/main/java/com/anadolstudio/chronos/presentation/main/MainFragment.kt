package com.anadolstudio.chronos.presentation.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentMainBinding
import com.anadolstudio.chronos.presentation.track.TrackBottom
import com.anadolstudio.chronos.view.button.feature.FeatureButton
import com.anadolstudio.core.groupie.BaseGroupAdapter
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.util.common.throttleClick
import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.xwray.groupie.Section

class MainFragment : BaseContentFragment<MainState, MainViewModel, MainController>(R.layout.fragment_main) {

    private companion object {
        const val RENDER_TRACK = "RENDER_TRACK"
    }

    override val viewStateDelegate: ViewStateDelegate = ViewStateDelegate()

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val trackSection: Section = Section()
    private val diagramSection: Section = Section()

    override fun createViewModelLazy() = viewModels<MainViewModel> { viewModelFactory }

    override fun initView() = with(binding) {
        initFragmentResultListeners(TrackBottom.TAG)
        calendarButton.throttleClick { controller.onCalendarClicked() }
        addButton.scaleAnimationOnClick { controller.onAddClicked() }
        chartButton.scaleAnimationOnClick { controller.onChartClicked() }
        stopWatcherButton.scaleAnimationOnClick { controller.onStopWatcherClicked() }
        editItemsButton.scaleAnimationOnClick { controller.onEditItemsClicked() }
        recycler.adapter = BaseGroupAdapter(diagramSection, trackSection)
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        TrackBottom.TAG -> controller.onTimeTracked()
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun render(state: MainState) {
        binding.addButton.setLoading(state.isLoading)
        renderStopWatcher(state.stopWatcherTime)
        renderTrack(state.trackState)
    }

    private fun renderStopWatcher(time: Time?) {
        val state = if (time == null) FeatureButton.State.IMAGE else FeatureButton.State.TEXT
        binding.stopWatcherButton.setState(state)
        val text = time?.run { getString(R.string.global_full_time_text, hoursString, minutesString, secondsString) }
        binding.stopWatcherButton.setText(text)
    }

    private fun renderTrack(trackState: TrackState) = trackState.render(RENDER_TRACK) {
        val items = trackState.notEmptyTrackRootList.map {
            TrackItem(trackRootUi = it, onClick = controller::onTrackClicked)
        }
        trackSection.update(items)
    }

}
