package com.anadolstudio.chronos.presentation.track

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomTrackBinding
import com.anadolstudio.chronos.presentation.categories.CategoriesBottom
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.create.CreateBottom
import com.anadolstudio.core.groupie.BaseGroupAdapter
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.util.common_extention.getParcelable
import com.anadolstudio.core.util.common_extention.requireParcelable
import com.anadolstudio.core.util.common_extention.setFragmentResult
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.assistedViewModel
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.xwray.groupie.Section

open class TrackBottom : BaseContentBottom<TrackState, TrackViewModel, TrackController>(R.layout.bottom_track) {

    companion object {
        const val TAG = "TrackBottom"
        private const val RENDER_CATEGORY = "RENDER_SELECTED_CATEGORY"
        private const val RENDER_BUTTON = "RENDER_BUTTON"
        private const val RENDER_LOADING = "RENDER_LOADING"
        private const val RENDER_LAST_TRACK = "RENDER_LAST_TRACK"
    }

    override val viewStateDelegate: ViewStateDelegate = ViewStateDelegate()

    protected val binding by viewBinding { BottomTrackBinding.bind(it) }
    protected val args: TrackBottomArgs by navArgs()
    private val section = Section()

    override fun getDialogTag(): String = TAG

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .trackViewModelFactory()
                .create(args.data)
    }

    override fun initView() = with(binding) {
        initFragmentResultListeners(TrackViewModel.CATEGORIES_REQUEST_KEY, CreateBottom.TAG)
        nameText.setOnIconClickListener(controller::onSearchButtonClicked)
        nameText.addValidateListener(controller::onNameChanged)
        hoursText.addValidateListener(controller::onHoursChanged)
        minutesText.addValidateListener(controller::onMinutesChanged)
        applyButton.scaleAnimationOnClick(action = controller::onTrackClicked)
        recycler.adapter = BaseGroupAdapter(section)
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        TrackViewModel.CATEGORIES_REQUEST_KEY -> controller.onCategoriesSelected(requireParcelable(data))
        CreateBottom.TAG -> controller.onCategoryCreated(requireParcelable(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is TrackBottomEvents.Result -> setFragmentResult(getDialogTag())
        else -> super.handleEvent(event)
    }

    override fun render(state: TrackState) = with(state) {
        binding.hoursText.setText(state.hours.toString(), withValidate = false)
        binding.minutesText.setText(state.minutes.toString(), withValidate = false)
        renderLastTracks(state.lastTrackList)
        renderSelectedCategory(selectedCategoryUi)
        renderLoading(isLoading)
        renderApplyButton(applyButtonState)
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

    private fun renderApplyButton(applyButtonState: ApplyButtonState) = applyButtonState.render(RENDER_BUTTON) {
        onRenderApplyButton(applyButtonState)
    }

    private fun renderLoading(isLoading: Boolean) = isLoading.render(RENDER_LOADING) {
        binding.nameText.isEnabled = !isLoading
        binding.hoursText.isEnabled = !isLoading
        binding.minutesText.isEnabled = !isLoading
        binding.applyButton.setLoading(isLoading)
    }

    protected open fun onRenderApplyButton(applyButtonState: ApplyButtonState) {
        val textRes = when (applyButtonState.hasSelectedCategory) {
            true -> R.string.global_add
            false -> R.string.global_create
        }
        binding.applyButton.setText(textRes)
        binding.applyButton.isEnabled = applyButtonState.isEnable
    }
}
