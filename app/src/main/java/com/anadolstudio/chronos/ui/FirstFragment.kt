package com.anadolstudio.chronos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentFirstBinding
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding

class FirstFragment: CoreBaseFragment<SimpleViewState>(R.layout.fragment_first) {

    override fun render(state: SimpleViewState) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentFirstBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

}
