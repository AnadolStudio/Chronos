package com.anadolstudio.chronos.ui.days

import android.os.Bundle
import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.BaseFragment
import com.anadolstudio.chronos.databinding.FragmentDaysBinding
import com.anadolstudio.chronos.extensions.navigateTo
import com.anadolstudio.chronos.navigation.Screens
import com.anadolstudio.chronos.ui.BaseViewState
import com.anadolstudio.core.common_extention.getDrawable
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.viewbinding.viewBinding

class DaysFragment : BaseFragment<BaseViewState<Unit>>(R.layout.fragment_days) {

    override fun render(state: BaseViewState<Unit>) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentDaysBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        binding.buttonFirst.setOnClickListener {
            navigateTo(Screens.SETTINGS_SCREEN)
        }
    }

    private fun setupToolbar() = binding.toolbar.apply {
        setBackClickListener { showMessageToast(SingleMessageToast.Short("toolbar")) }
        addIconButton(getDrawable(R.drawable.ic_add)) { showMessageToast(SingleMessageToast.Short("add")) }
        addIconButton(getDrawable(R.drawable.ic_settings)) { showMessageToast(SingleMessageToast.Short("settings")) }
    }

}
