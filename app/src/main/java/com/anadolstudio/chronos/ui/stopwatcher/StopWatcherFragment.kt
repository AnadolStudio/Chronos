package com.anadolstudio.chronos.ui.stopwatcher

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentSettingsBinding
import com.anadolstudio.chronos.ui.BaseViewState
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding

class StopWatcherFragment : CoreBaseFragment<BaseViewState<Unit>>(R.layout.fragment_settings) {

    override fun render(state: BaseViewState<Unit>) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
