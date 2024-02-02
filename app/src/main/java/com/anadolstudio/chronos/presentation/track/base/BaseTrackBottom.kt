package com.anadolstudio.chronos.presentation.track.base

import android.os.Bundle
import androidx.core.view.isVisible
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomTrackBinding
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.create.CreateBottom
import com.anadolstudio.chronos.presentation.track.LastTrackItem
import com.anadolstudio.chronos.presentation.track.TrackBottomEvents
import com.anadolstudio.core.groupie.BaseGroupAdapter
import com.anadolstudio.core.util.common_extention.requireParcelable
import com.anadolstudio.core.util.common_extention.setFragmentResult
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.xwray.groupie.Section

abstract class BaseTrackBottom<
        State : BaseTrackState,
        VM : BaseTrackViewModel<State>,
        Controller : BaseTrackController
        > : BaseContentBottom<State, VM, Controller>(R.layout.bottom_track) {

    companion object {
        private const val RENDER_CATEGORY = "RENDER_SELECTED_CATEGORY"
        private const val RENDER_LOADING = "RENDER_LOADING"
        private const val RENDER_LAST_TRACK = "RENDER_LAST_TRACK"
    }

    protected val binding by viewBinding { BottomTrackBinding.bind(it) }
    private val section = Section()

    override fun initView() = with(binding) {
        initFragmentResultListeners(BaseTrackViewModel.CATEGORIES_REQUEST_KEY, CreateBottom.TAG)
        nameText.setOnIconClickListener(controller::onSearchButtonClicked)
        nameText.addValidateListener(controller::onNameChanged)
        hoursText.addValidateListener(controller::onHoursChanged)
        minutesText.addValidateListener(controller::onMinutesChanged)
        applyButton.scaleAnimationOnClick(action = controller::onTrackClicked)
        hourPlus.scaleAnimationOnClick { controller.onHourPlusClicked() }
        hourMinus.scaleAnimationOnClick { controller.onHourMinusClicked() }
        minutes10Plus.scaleAnimationOnClick { controller.onMinutesPlusClicked() }
        minutes10Minus.scaleAnimationOnClick { controller.onMinutesMinusClicked() }
        roundButton.scaleAnimationOnClick { controller.onRoundClicked() }
        recycler.adapter = BaseGroupAdapter(section)
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        BaseTrackViewModel.CATEGORIES_REQUEST_KEY -> controller.onCategoriesSelected(requireParcelable(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is TrackBottomEvents.Result -> setFragmentResult(getDialogTag())
        else -> super.handleEvent(event)
    }

    override fun render(state: State) = with(state) {
        binding.hoursText.setText(state.hours.toString(), withValidate = false)
        if (hours == 0) binding.hoursText.setSelectorToEnd()
        binding.minutesText.setText(state.minutes.toString(), withValidate = false)
        if (minutes == 0) binding.minutesText.setSelectorToEnd()
        binding.nameText.setText(state.name, withValidate = false)
        renderLastTracks(state.lastTrackList)
        renderSelectedCategory(selectedCategoryUi)
        renderLoading(isLoading)
    }

    private fun renderLastTracks(lastTrackList: List<CategoryUi>) = lastTrackList.render(RENDER_LAST_TRACK) {
        binding.lastTrackGroup.isVisible = lastTrackList.isNotEmpty()

        val items = lastTrackList.map {
            LastTrackItem(categoryUi = it, onClick = controller::onCategoriesSelected)
        }
        section.update(items)
    }

    private fun renderSelectedCategory(selectedCategoryUi: CategoryUi?) = selectedCategoryUi.render(RENDER_CATEGORY) {
        binding.currentItem.root.isVisible = this != null

        if (this != null) {
            binding.currentItem.categoryVew.setup(this)
            binding.nameText.setText(name, withValidate = false)
        }
    }

    private fun renderLoading(isLoading: Boolean) = isLoading.render(RENDER_LOADING) {
        binding.nameText.isEnabled = !isLoading
        binding.hoursText.isEnabled = !isLoading
        binding.minutesText.isEnabled = !isLoading
        binding.applyButton.setLoading(isLoading)
    }

}
