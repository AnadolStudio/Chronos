package com.anadolstudio.chronos.ui.days

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.BaseFragment
import com.anadolstudio.chronos.databinding.FragmentDaysBinding
import com.anadolstudio.core.common_extention.getDrawable
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.obtainViewModel

class DaysFragment : BaseFragment<Unit, DaysViewModel>(R.layout.fragment_days) {

    override fun showContent(content: Unit) {

    }

    override fun showLoading() {

    }

    override fun createViewModel(): DaysViewModel = obtainViewModel(DaysViewModel.Factory())

    private val binding by viewBinding(FragmentDaysBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
    }

    private fun setupToolbar() = binding.toolbar.apply {
        setBackClickListener { showMessageToast(SingleMessageToast.Short("toolbar")) }
        addIconButton(getDrawable(R.drawable.ic_add)) { showMessageToast(SingleMessageToast.Short("add")) }
        addIconButton(getDrawable(R.drawable.ic_settings)) { showMessageToast(SingleMessageToast.Short("settings")) }
    }

}
