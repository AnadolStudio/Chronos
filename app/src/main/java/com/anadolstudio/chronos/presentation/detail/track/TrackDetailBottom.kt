package com.anadolstudio.chronos.presentation.detail.track

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomTrackDetailBinding
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.util.common_extention.setFragmentResult
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.assistedViewModel
import com.anadolstudio.core.viewmodel.livedata.SingleEvent

open class TrackDetailBottom : BaseContentBottom<TrackDetailState, TrackDetailViewModel, TrackDetailController>(R.layout.bottom_track_detail) {

    override val viewStateDelegate: ViewStateDelegate = ViewStateDelegate()

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
