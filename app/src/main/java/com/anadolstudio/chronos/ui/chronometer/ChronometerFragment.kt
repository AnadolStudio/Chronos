package com.anadolstudio.chronos.ui.chronometer

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentSettingsBinding
import com.anadolstudio.chronos.databinding.FragmentStopWatcherBinding
import com.anadolstudio.chronos.ui.BaseViewState
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding

class ChronometerFragment : CoreBaseFragment<BaseViewState<Unit>>(R.layout.fragment_stop_watcher) {

    override fun render(state: BaseViewState<Unit>) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentStopWatcherBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setBackIconVisible(true)
        binding.toolbar.setBackClickListener { findNavController().navigateUp() }
    }

}
