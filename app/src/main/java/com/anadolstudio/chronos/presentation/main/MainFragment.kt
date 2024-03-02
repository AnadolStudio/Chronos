package com.anadolstudio.chronos.presentation.main

import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.viewModels
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentMainBinding
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.CALENDAR_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.CATEGORIES_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.EDIT_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.TRACK_CHANGED_REQUEST_KEY
import com.anadolstudio.chronos.presentation.stopwatcher.StopWatcherFragment
import com.anadolstudio.chronos.presentation.track.TrackBottom
import com.anadolstudio.chronos.view.diagram.ProgressData
import com.anadolstudio.utils.util.common.throttleClick
import com.anadolstudio.utils.util.data_time.Time
import com.anadolstudio.utils.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.view.gesture.HorizontalMoveGesture
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.ui.adapters.groupie.BaseGroupAdapter
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.utils.util.extentions.getDrawable
import com.anadolstudio.utils.util.extentions.requireLong
import com.anadolstudio.utils.util.extentions.requireParcelable
import com.xwray.groupie.Section
import java.util.concurrent.TimeUnit

class MainFragment : BaseContentFragment<MainState, MainViewModel, MainController>(R.layout.fragment_main) {

    private companion object {
        val MINUTES_IN_DAY = TimeUnit.DAYS.toMinutes(1).toInt()
        const val RENDER_TRACK = "RENDER_TRACK"
    }

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val stopWatcherSection: Section = Section()
    private val trackSection: Section = Section()
    private val diagramSection: Section = Section()

    override fun createViewModelLazy() = viewModels<MainViewModel> { viewModelFactory }

    private val horizontalMoveGestureDetector: GestureDetector by lazy {
        GestureDetector(
                context,
                HorizontalMoveGesture(
                        width = binding.recycler.width,
                        onSwipeLeft = controller::onNextDateSelected,
                        onSwipeRight = controller::onPreviousDateSelected
                )
        )
    }

    override fun initView() = with(binding) {
        initFragmentResultListeners(
                TrackBottom.TAG,
                StopWatcherFragment.TAG,
                CATEGORIES_REQUEST_KEY,
                EDIT_REQUEST_KEY,
                TRACK_CHANGED_REQUEST_KEY,
                CALENDAR_REQUEST_KEY
        )
        calendarButton.throttleClick { controller.onCalendarClicked() }
        addButton.scaleAnimationOnClick { controller.onAddClicked() }
        editButton.throttleClick { controller.onEditItemsClicked() }
        nightButton.throttleClick { controller.onChangeNightModeClicked() }
        recycler.adapter = BaseGroupAdapter(stopWatcherSection, diagramSection, trackSection)
        binding.recyclerContainer.addDispatchTouchListener { _, event ->
            horizontalMoveGestureDetector.onTouchEvent(event)
        }
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        CATEGORIES_REQUEST_KEY -> controller.onCategoriesSelected(requireParcelable(data))
        TrackBottom.TAG,
        StopWatcherFragment.TAG,
        EDIT_REQUEST_KEY,
        TRACK_CHANGED_REQUEST_KEY -> controller.onTimeTrackChanged()
        CALENDAR_REQUEST_KEY -> controller.onDateSelected(requireLong(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun render(state: MainState) {
        renderNightModeButton(state)
        binding.addButton.setLoading(state.isLoading)
        renderStopWatcher(state.stopWatcherData, state.stopWatcherTime)
        renderTrack(state.trackState)
    }

    private fun renderNightModeButton(state: MainState) {
        val drawableRes = when (state.isNightMode) {
            true -> R.drawable.ic_mode_light
            false -> R.drawable.ic_mode_dark
        }

        binding.nightButton.setDrawable(getDrawable(drawableRes))
    }

    private fun renderStopWatcher(data: StopWatcherData, time: Time?) {
        val item = StopWatcherItem(
                data = data,
                time = time,
                onClick = controller::onStopWatcherClicked,
                onStopWatcherToggleClick = controller::onStopWatcherToggleClicked,
        )
        stopWatcherSection.update(listOf(item))
    }

    private fun renderTrack(trackState: TrackState) = trackState.render(RENDER_TRACK) {
        val trackItems = trackState.notEmptyTrackRootList
                .map { TrackItem(trackRootUi = it, onClick = controller::onTrackClicked) }
                .ifEmpty { listOf(TrackStubItem()) }

        trackSection.update(trackItems)

        val totalMinutes = trackState.notEmptyTrackRootList.sumOf { it.time.totalMinutes }

        val progressDataList = trackState.notEmptyTrackRootList.map {
            ProgressData(color = it.color, value = it.time.totalMinutes)
        }.toMutableList()

        if (totalMinutes < MINUTES_IN_DAY) {
            val other = ProgressData(
                    color = requireContext().getColor(R.color.disableBackground),
                    value = MINUTES_IN_DAY - totalMinutes
            )

            progressDataList.add(other)
        }

        val diagramItem = DiagramItem(
                data = DiagramItem.Data(
                        hours = totalMinutes / 60F,
                        nextDateEnable = nextDateEnable,
                        currentDate = currentDate,
                        progressDataList = progressDataList,
                        onNextDateClick = controller::onNextDateSelected,
                        onPreviousDateClick = controller::onPreviousDateSelected,
                ),
                onClick = controller::onDiagramClicked
        )

        diagramSection.update(listOf(diagramItem))
    }

}
