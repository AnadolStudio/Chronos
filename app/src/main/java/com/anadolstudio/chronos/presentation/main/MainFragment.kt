package com.anadolstudio.chronos.presentation.main

import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.fragment.BaseContentFragment
import com.anadolstudio.chronos.databinding.FragmentMainBinding
import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.util.common.throttleClick
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding

class MainFragment : BaseContentFragment<MainState, MainViewModel, MainController>(R.layout.fragment_main) {

    override val viewStateDelegate: ViewStateDelegate = ViewStateDelegate()

    private val binding by viewBinding { FragmentMainBinding.bind(it) }

    override fun initView() = with(binding) {
        calendarButton.throttleClick { controller.onCalendarClicked() }
        addButton.scaleAnimationOnClick { controller.onAddClicked() }
        chartButton.scaleAnimationOnClick { controller.onChartClicked() }
        stopWatcherButton.scaleAnimationOnClick { controller.onStopWatcherClicked() }
        editItemsButton.scaleAnimationOnClick { controller.onEditItemsClicked() }
    }

    override fun render(state: MainState) = Unit

    override fun createViewModel() = MainViewModel()

}