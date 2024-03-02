package com.anadolstudio.chronos.presentation.detail.track

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomTrackDetailBinding
import com.anadolstudio.ui.fragment.state_util.ViewStateDelegate
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.utils.util.extentions.setFragmentResult

open class TrackDetailBottom : BaseContentBottom<TrackDetailState, TrackDetailViewModel, TrackDetailController>(R.layout.bottom_track_detail) {

    protected val binding by viewBinding { BottomTrackDetailBinding.bind(it) }
    protected val args: TrackDetailBottomArgs by navArgs()

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .trackDetailViewModelFactory()
                .create(args.data)
    }

    override fun initView() = initFragmentResultListeners(TrackDetailViewModel.EDIT_TRACK_REQUEST_KEY)

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        TrackDetailViewModel.EDIT_TRACK_REQUEST_KEY -> controller.onTrackChanged()
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is TrackDetailBottomEvents.Result -> setFragmentResult(args.data.requestKey)
        else -> super.handleEvent(event)
    }

    override fun render(state: TrackDetailState) = with(state) {
        binding.topLine.backgroundTintList = ColorStateList.valueOf(trackRootUi.color)
        binding.trackView.setup(trackRootUi = trackRootUi, onChildClick = controller::onTrackClicked)
    }

}
