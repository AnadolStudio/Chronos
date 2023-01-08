package com.anadolstudio.chronos.ui.settings

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentSettingsBinding
import com.anadolstudio.chronos.ui.BaseViewState
import com.anadolstudio.core.fragment.CoreBaseFragment
import com.anadolstudio.core.viewbinding.viewBinding

class SettingsFragment : CoreBaseFragment<BaseViewState<Unit>>(R.layout.fragment_settings), ColorPickerBottomSheet.ColorPick {

    override fun render(state: BaseViewState<Unit>) {
        TODO("Not yet implemented")
    }

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setBackIconVisible(true)
        binding.toolbar.setBackClickListener { findNavController().navigateUp() }
        binding.colorContainer.setOnClickListener {
            ColorPickerBottomSheet.newInstance(Color.BLUE)
                    .show(childFragmentManager)
        }
    }

    override fun onColorPick(color: Int) = binding.colorButton.setColor(color)
}
