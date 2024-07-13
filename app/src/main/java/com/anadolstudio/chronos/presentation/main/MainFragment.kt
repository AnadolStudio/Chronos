package com.anadolstudio.chronos.presentation.main

import android.os.Bundle
import android.view.GestureDetector
import androidx.fragment.app.viewModels
import androidx.transition.TransitionManager
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentMainBinding
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_ADD_TRACK_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_CALENDAR_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_CATEGORIES_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_EDIT_REQUEST_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_STOP_WATCHER_KEY
import com.anadolstudio.chronos.presentation.main.MainViewModel.Companion.MAIN_TRACK_CHANGED_REQUEST_KEY
import com.anadolstudio.chronos.util.SimpleScrollListener
import com.anadolstudio.chronos.view.diagram.ProgressData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.ui.SingleMessageSnack
import com.anadolstudio.ui.adapters.groupie.BaseGroupAdapter
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.utils.util.common.throttleClick
import com.anadolstudio.utils.util.data_time.Time
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
        const val RENDER_FAB = "RENDER_FAB"
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
                MAIN_ADD_TRACK_KEY,
                MAIN_STOP_WATCHER_KEY,
                MAIN_CATEGORIES_REQUEST_KEY,
                MAIN_EDIT_REQUEST_KEY,
                MAIN_TRACK_CHANGED_REQUEST_KEY,
                MAIN_CALENDAR_REQUEST_KEY
        )
        calendarButton.throttleClick { controller.onCalendarClicked() }
        addButton.setOnClickListener { controller.onAddClicked() }
        calendar.setOnClickListener { showMessageSnackbar(SingleMessageSnack.Short("test")) }
        editButton.throttleClick { controller.onEditItemsClicked() }
        nightButton.throttleClick { controller.onChangeNightModeClicked() }
        recycler.adapter = BaseGroupAdapter(diagramSection, stopWatcherSection, trackSection)
        binding.recyclerContainer.addDispatchTouchListener { _, event ->
            horizontalMoveGestureDetector.onTouchEvent(event)
        }

        binding.recycler.addOnScrollListener(
                SimpleScrollListener(
                        onScrollStateChanged = { recycler, _ -> controller.onRecyclerScrollStateChanged(recycler) },
                        onScrolled = { recycler, _, _ , _ -> controller.onRecyclerScrollStateChanged(recycler) }
                )
        )
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
        renderNightModeButton(state)
//        binding.addButton.setLoading(state.isLoading)
        renderStopWatcher(state.stopWatcherData, state.stopWatcherTime)
        renderTrack(state.trackState)
        renderFab(state.isFabExtended)
    }

    private fun renderFab(isExpanded: Boolean) = isExpanded.render(RENDER_FAB) {
        TransitionManager.beginDelayedTransition(binding.addButtonContainer)
        binding.addButton.isExtended = isExpanded
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
