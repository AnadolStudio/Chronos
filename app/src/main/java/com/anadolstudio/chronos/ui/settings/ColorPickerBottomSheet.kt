package com.anadolstudio.chronos.ui.settings

import android.os.Bundle
import android.view.View
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.databinding.FragmentColorPickerBinding
import com.anadolstudio.core.common_extention.toColorHex
import com.anadolstudio.core.common_util.throttleClick
import com.anadolstudio.core.dialogs.BaseBottomDialogFragment
import com.anadolstudio.core.viewbinding.viewBinding

class ColorPickerBottomSheet : BaseBottomDialogFragment(R.layout.fragment_color_picker) {

    companion object {
        private const val TAG = "ColorPickerBottomSheet"
        private const val COLOR_KEY = "color_key"

        fun newInstance(color: Int? = null): ColorPickerBottomSheet = ColorPickerBottomSheet().apply {
            if (color == null) return@apply
            arguments = Bundle().apply { putInt(COLOR_KEY, color) }
        }
    }

    private val binding by viewBinding(FragmentColorPickerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val color = arguments?.getInt(COLOR_KEY)?.also { defaultColor ->
            binding.colorPicker.pickedColor = IntegerHSLColor().apply { setFromColorInt(defaultColor) }
        } ?: let {
            binding.colorPicker.pickedColor.toColorInt()
        }

        binding.colorValue.text = color.toColorHex()
        binding.colorPicker.addListener(
                ColorChangeListener { newColor -> binding.colorValue.text = newColor.toColorHex() }
        )

        binding.applyButton.throttleClick {
            (parentFragment as? ColorPick)?.onColorPick(binding.colorPicker.pickedColor.toColorInt())
            dismiss()
        }
    }

    override fun getDialogTag(): String = TAG

    private class ColorChangeListener(
            val action: (color: Int) -> Unit
    ) : ColorSeekBar.DefaultOnColorPickListener<ColorSeekBar<IntegerHSLColor>, IntegerHSLColor>() {

        override fun onColorPicking(
                picker: ColorSeekBar<IntegerHSLColor>, color: IntegerHSLColor, value: Int, fromUser: Boolean
        ) = action.invoke(color.toColorInt())
    }

    interface ColorPick {

        fun onColorPick(color: Int)

    }

}
