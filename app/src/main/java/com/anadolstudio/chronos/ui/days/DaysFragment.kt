package com.anadolstudio.chronos.ui.days

import android.os.Bundle
import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.BaseFragment
import com.anadolstudio.chronos.databinding.FragmentDaysBinding
import com.anadolstudio.chronos.extensions.navigateTo
import com.anadolstudio.chronos.navigation.Screens
import com.anadolstudio.chronos.ui.BaseViewState
import com.anadolstudio.core.viewbinding.viewBinding

class DaysFragment : BaseFragment<BaseViewState<Unit>>(R.layout.fragment_days) {

    override fun render(state: BaseViewState<Unit>) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentDaysBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            navigateTo(Screens.SETTINGS_SCREEN)
        }
    }

}
