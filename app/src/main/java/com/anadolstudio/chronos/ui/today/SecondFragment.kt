package com.anadolstudio.chronos.ui.today

import android.os.Bundle
import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentSecondBinding
import com.anadolstudio.chronos.navigation.Screens
import com.anadolstudio.chronos.extensions.navigateTo
import com.anadolstudio.chronos.ui.SimpleViewState
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding

class SecondFragment: CoreBaseFragment<SimpleViewState>(R.layout.fragment_second){

    override fun render(state: SimpleViewState) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentSecondBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            navigateTo(Screens.FIRST_SCREEN)
        }
    }

}
