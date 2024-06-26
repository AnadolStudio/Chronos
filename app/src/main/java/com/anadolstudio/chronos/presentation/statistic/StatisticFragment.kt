package com.anadolstudio.chronos.presentation.statistic

import android.os.Bundle
import android.view.GestureDetector
import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentStatisticBinding
import com.anadolstudio.chronos.presentation.main.TrackItem
import com.anadolstudio.chronos.presentation.main.TrackStubItem
import com.anadolstudio.chronos.presentation.statistic.StatisticViewModel.Companion.CALENDAR_REQUEST_KEY
import com.anadolstudio.chronos.view.diagram.ProgressData
import com.anadolstudio.ui.adapters.groupie.BaseGroupAdapter
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.utils.util.extentions.requireLongList
import com.anadolstudio.view.gesture.HorizontalMoveGesture
import com.xwray.groupie.Section

class StatisticFragment : BaseContentFragment<StatisticState, StatisticViewModel, StatisticController>(R.layout.fragment_statistic) {

    private companion object {
        const val RENDER_TRACK = "RENDER_TRACK"
    }

    private val binding by viewBinding { FragmentStatisticBinding.bind(it) }
    private val args: StatisticFragmentArgs by navArgs()

    private val trackSection: Section = Section()
    private val diagramSection: Section = Section()
    private val horizontalMoveGestureDetector: GestureDetector by lazy {
        GestureDetector(
                context,
                HorizontalMoveGesture(
                        width = binding.recycler.width,
                        onSwipeLeft = controller::onNextSwiped,
                        onSwipeRight = controller::onPreviousSwiped
                )
        )
    }

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
            .statisticViewModelFactory()
            .create(args.data)
    }

    override fun initView() = with(binding) {
        initFragmentResultListeners(CALENDAR_REQUEST_KEY)
        binding.toolbar.setBackClickListener { controller.onBackClicked() }
        recycler.adapter = BaseGroupAdapter(diagramSection, trackSection)
        binding.recyclerContainer.addDispatchTouchListener { _, event ->
            horizontalMoveGestureDetector.onTouchEvent(event)
        }
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        CALENDAR_REQUEST_KEY -> controller.onPeriodSelected(requireLongList(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun render(state: StatisticState) = state.render(RENDER_TRACK) {
        val trackItems = trackState.notEmptyTrackRootList
                .map { TrackItem(trackRootUi = it) }
                .ifEmpty { listOf(TrackStubItem()) }

        trackSection.update(trackItems)

        val totalMinutes = trackState.notEmptyTrackRootList.sumOf { it.time.totalMinutes }

        val progressDataList = trackState.notEmptyTrackRootList.map {
            ProgressData(color = it.color, value = it.time.totalMinutes)
        }.toMutableList()

        if (totalMinutes < totalMinutesFromPeriod) {
            val other = ProgressData(
                    color = requireContext().getColor(R.color.disableBackground),
                    value = totalMinutesFromPeriod.toInt() - totalMinutes
            )

            progressDataList.add(other)
        }

        val diagramItem = DiagramItem(
                data = DiagramItem.Data(
                        hours = totalMinutes / 60F,
                        totalMinutes = totalMinutesFromPeriod,
                        fromDate = fromDate,
                        toDate = toDate,
                        progressDataList = progressDataList,
                        onPeriodClick = controller::onPeriodClicked,
                ),
        )

        diagramSection.update(listOf(diagramItem))
    }
}
