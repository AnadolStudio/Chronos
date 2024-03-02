package com.anadolstudio.chronos.presentation.create

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomCreateCategoryBinding
import com.anadolstudio.chronos.presentation.categories.CategoriesBottom
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.ui.fragment.state_util.ViewStateDelegate
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.utils.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.utils.util.extentions.getParcelable
import com.anadolstudio.utils.util.extentions.setFragmentResult

open class CreateBottom : BaseContentBottom<CreateState, CreateViewModel, CreateController>(R.layout.bottom_create_category) {

    companion object {
        const val TAG = "CreateBottom"
        private const val RENDER_CATEGORY = "RENDER_SELECTED_CATEGORY"
        private const val RENDER_LOADING = "RENDER_LOADING"
    }

    protected val binding by viewBinding { BottomCreateCategoryBinding.bind(it) }
    protected val args: CreateBottomArgs by navArgs()

    override fun getDialogTag(): String = TAG

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .createViewModelFactory()
                .create(args.data)
    }

    override fun initView() = with(binding) {
        initFragmentResultListeners(CreateViewModel.CATEGORIES_REQUEST_KEY)
        settingsContainer.categoryNameText.setDrawableEnd(null)
        settingsContainer.categoryNameText.addValidateListener(controller::onNameChanged)
        settingsContainer.parentNameText.setOnIconClickListener(controller::onSearchButtonClicked)
        settingsContainer.parentNameText.addValidateListener(controller::onParentNameChanged)
        applyButton.scaleAnimationOnClick(action = controller::onCreateClicked)
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        CreateViewModel.CATEGORIES_REQUEST_KEY -> controller.onCategoriesSelected(getParcelable(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is CreateBottomEvents.Result -> setFragmentResult(getDialogTag(), event.category)
        else -> super.handleEvent(event)
    }

    override fun render(state: CreateState) = with(state) {
        binding.settingsContainer.categoryNameText.setText(name, withValidate = false)
        binding.applyButton.isEnabled = state.createEnable
        renderSelectedCategory(parentCategoryUi)
        renderLoading(isLoading)
    }

    private fun renderSelectedCategory(selectedCategoryUi: CategoryUi?) = selectedCategoryUi.render(RENDER_CATEGORY) {
        binding.settingsContainer.parentItem.root.isVisible = this != null

        if (this != null) {
            binding.settingsContainer.parentItem.categoryVew.setup(this)
            binding.settingsContainer.parentNameText.setText(name, withValidate = false)
        }
    }

    private fun renderLoading(isLoading: Boolean) = isLoading.render(RENDER_LOADING) {
        binding.settingsContainer.categoryNameText.isEnabled = !isLoading
        binding.settingsContainer.parentNameText.isEnabled = !isLoading
        binding.applyButton.setLoading(isLoading)
    }

}
