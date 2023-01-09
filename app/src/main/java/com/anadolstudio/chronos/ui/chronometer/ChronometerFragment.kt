package com.anadolstudio.chronos.ui.chronometer

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentStopWatcherBinding
import com.anadolstudio.chronos.ui.days.DaysViewModel
import com.anadolstudio.core.viewmodel.BaseViewState
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.obtainViewModel

class ChronometerFragment : CoreBaseFragment<Unit, DaysViewModel>(R.layout.fragment_stop_watcher) {

    override fun showContent(content: Unit) {

    }

    override fun showLoading() {

    }

    override fun createViewModel(): DaysViewModel = obtainViewModel(DaysViewModel.Factory())


    private val binding by viewBinding(FragmentStopWatcherBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setBackIconVisible(true)
        binding.toolbar.setBackClickListener { findNavController().navigateUp() }

        binding.chronometerView.addListeners(
                onAddButtonAction = { showMessageToast(SingleMessageToast.Short("add")) },
                onRemoveButtonAction = {
                    binding.chronometerView.setup()
                    showMessageToast(SingleMessageToast.Short("remove"))
                },
                onStartButtonAction = { showMessageToast(SingleMessageToast.Short("start")) },
                onStopButtonAction = { showMessageToast(SingleMessageToast.Short("stop")) }
        )
    }

}
