package com.anadolstudio.chronos.presentation.main

import android.os.Bundle
import android.view.GestureDetector
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentMainBinding
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_ADD_TRACK_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_CALENDAR_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_CATEGORIES_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_EDIT_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_STOP_WATCHER_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_TRACK_CHANGED_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.behavior.MainAppBarBehavior
import com.anadolstudio.chronos.presentation.main.behavior.ScrollState
import com.anadolstudio.chronos.util.toWeekDayDateFormat
import com.anadolstudio.chronos.view.diagram.ProgressData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.ui.adapters.groupie.BaseGroupAdapter
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.utils.util.common.throttleClick
import com.anadolstudio.utils.util.data_time.Time
import com.anadolstudio.utils.util.extentions.getCompatDrawable
import com.anadolstudio.utils.util.extentions.getDrawable
import com.anadolstudio.utils.util.extentions.requireLong
import com.anadolstudio.utils.util.extentions.requireParcelable
import com.anadolstudio.view.gesture.HorizontalMoveGesture
import com.xwray.groupie.Section
import java.util.concurrent.TimeUnit

class MainFragment : BaseContentFragment<MainState, MainViewModel, MainController>(R.layout.fragment_main) {

    private companion object {
        val MINUTES_IN_DAY = TimeUnit.DAYS.toMinutes(1).toInt()
        const val RENDER_TRACK = "RENDER_TRACK"
        const val RENDER_DIAGRAM = "RENDER_DIAGRAM"
        const val RENDER_STOP_WATCHER = "RENDER_STOP_WATCHER"
        const val RENDER_STOP_WATCHER_TIME = "RENDER_STOP_WATCHER_TIME"
    }

    private val binding by viewBinding { FragmentMainBinding.bind(it) }
    private val trackSection: Section = Section()

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
                MAIN_ADD_TRACK_KEY,
                MAIN_STOP_WATCHER_KEY,
                MAIN_CATEGORIES_REQUEST_KEY,
                MAIN_EDIT_REQUEST_KEY,
                MAIN_TRACK_CHANGED_REQUEST_KEY,
                MAIN_CALENDAR_REQUEST_KEY
        )
        editButton.throttleClick { controller.onEditItemsClicked() }
        calendarButton.throttleClick { controller.onCalendarClicked() }
        stopWatcherButton.throttleClick { controller.onStopWatcherClicked() }
        diagram.throttleClick { controller.onDiagramClicked() }
        nightButton.throttleClick { controller.onChangeNightModeClicked() }

        appBarAddButton.throttleClick { controller.onAddClicked() }
        bottomAddButton.throttleClick { controller.onAddClicked() }

        recycler.adapter = BaseGroupAdapter(trackSection)
        binding.recyclerContainer.addDispatchTouchListener { _, event ->
            horizontalMoveGestureDetector.onTouchEvent(event)
        }

        val appbarLayoutParams = (appBar.layoutParams as? CoordinatorLayout.LayoutParams)
        (appbarLayoutParams?.behavior as? MainAppBarBehavior)?.scrollStateListener = controller::onAppBarScrollStateChanged
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        MAIN_CATEGORIES_REQUEST_KEY -> controller.onCategoriesSelected(requireParcelable(data))
        MAIN_ADD_TRACK_KEY,
        MAIN_STOP_WATCHER_KEY,
        MAIN_EDIT_REQUEST_KEY,
        MAIN_TRACK_CHANGED_REQUEST_KEY -> controller.onTimeTrackChanged()

        MAIN_CALENDAR_REQUEST_KEY -> controller.onDateSelected(requireLong(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun render(state: MainState) {
        if (state.appBarScrollState != ScrollState.IDLE) return

        renderNightModeButton(state)
        renderStopWatcher(state.stopWatcherData, state.stopWatcherTime)
        renderDiagram(state.trackState)
        renderTrack(state.trackState)
    }

    private fun renderNightModeButton(state: MainState) {
        val drawableRes = when (state.isNightMode) {
            true -> R.drawable.ic_mode_light
            false -> R.drawable.ic_mode_dark
        }

        binding.nightButton.setDrawable(getDrawable(drawableRes))
    }

    private fun renderStopWatcher(data: StopWatcherData, time: Time?) = data.render(
            RENDER_STOP_WATCHER,
            RENDER_STOP_WATCHER_TIME to time
    ) {
        val drawable = when (data.state) {
            StopWatcherData.State.IN_PROGRESS -> R.drawable.ic_pause
            StopWatcherData.State.RESULT -> R.drawable.ic_play
            StopWatcherData.State.DEFAULT -> R.drawable.ic_stop_watcher
        }

        binding.stopWatcherButton.setDrawable(requireContext().getCompatDrawable(drawable))

        val text = when (data.state) {
            StopWatcherData.State.IN_PROGRESS,
            StopWatcherData.State.RESULT -> time
                    ?.run {
                        requireContext().getString(
                                R.string.global_full_time_text,
                                hoursString,
                                minutesString,
                                secondsString
                        )
                    }
                    ?: requireContext().getText(R.string.stop_watcher)

            StopWatcherData.State.DEFAULT -> requireContext().getText(R.string.stop_watcher)
        }
        binding.stopWatcherButton.setText(text)
    }

    private fun renderTrack(trackState: TrackState) = trackState.render(RENDER_TRACK) {
        val trackItems = trackState.notEmptyTrackRootList
                .map { TrackItem(trackRootUi = it, onClick = controller::onTrackClicked) }
                .ifEmpty { listOf(TrackStubItem()) }

        trackSection.update(trackItems)
    }

    private fun renderDiagram(trackState: TrackState) = trackState.render(RENDER_DIAGRAM) {
        val title = trackState.currentDate.toWeekDayDateFormat()
        binding.todayText.text = title
        binding.toolbar.setTitle(title)

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

        binding.diagram.setup(
                nextDateEnable = nextDateEnable,
                currentDate = currentDate,
                progressDataList = progressDataList,
                onNextDateClick = controller::onNextDateSelected,
                onPreviousDateClick = controller::onPreviousDateSelected,
        )
    }

}
