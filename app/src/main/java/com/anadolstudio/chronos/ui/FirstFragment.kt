package com.anadolstudio.chronos.ui

import android.os.Bundle
import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentFirstBinding
import com.anadolstudio.chronos.navigation.Screens
import com.anadolstudio.chronos.extensions.navigate
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding

class FirstFragment : CoreBaseFragment<SimpleViewState>(R.layout.fragment_first) {

    override fun render(state: SimpleViewState) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentFirstBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            navigate(Screens.SECOND_SCREEN)
        }
    }

}
