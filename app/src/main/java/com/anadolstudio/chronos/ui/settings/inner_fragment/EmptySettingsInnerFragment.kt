package com.anadolstudio.chronos.ui.settings.inner_fragment

import android.os.Bundle
import android.view.View
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.BaseFragment
import com.anadolstudio.chronos.databinding.FragmentSettingsItemBinding
import com.anadolstudio.chronos.ui.settings.ColorPickerBottomSheet
import com.anadolstudio.core.common_util.throttleClick
import com.anadolstudio.core.fragment.withArgs
import com.anadolstudio.core.view.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.core.viewbinding.viewBinding
import com.anadolstudio.core.viewmodel.obtainViewModel
import com.anadolstudio.domain.models.settings.SettingsCategoryModel
import com.anadolstudio.domain.models.settings.SettingsCategoryModel.createEmptyCategory

class EmptySettingsInnerFragment :  BaseFragment<SettingsInnerContent, EmptySettingsInnerViewModel>(R.layout.fragment_settings_item), ColorPickerBottomSheet.ColorPick {

    companion object {
        private const val CATEGORY_KEY = "category_key"

        fun newInstance(category: SettingsCategoryModel.Category): EmptySettingsInnerFragment = EmptySettingsInnerFragment().withArgs {
            putParcelable(CATEGORY_KEY, category)
        }

    }

    private val binding by viewBinding(FragmentSettingsItemBinding::bind)

    override fun showContent(content: SettingsInnerContent) {
        binding.settingsCategoryItem.setTitle(requireContext().getString(R.string.settings_empty_category_title))
        binding.settingsCategoryItem.scaleAnimationOnClick { addCategory() }

        updateColor(content.category.color)
    }

    private fun addCategory() {
        requireView().clearFocus()
        requireView().post { viewModel.addCategory() }
    }

    override fun showLoading() = Unit

    override fun createViewModel(): EmptySettingsInnerViewModel {
        val category = arguments?.getParcelable(CATEGORY_KEY) ?: createEmptyCategory()

        return obtainViewModel(EmptySettingsInnerViewModel.Factory(category))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.colorContainer.setOnClickListener {
            ColorPickerBottomSheet.newInstance(binding.colorButton.getColor()).show(childFragmentManager)
        }

        binding.addSubcategoryButton.throttleClick {}
        binding.nameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) viewModel.changeName(binding.nameEditText.text.toString())
        }
    }

    private fun updateColor(color: Int) {
        binding.settingsCategoryItem.setColor(color)
        binding.colorButton.setColor(color)
    }

    override fun onColorPick(color: Int) = viewModel.changeColor(color)
}
