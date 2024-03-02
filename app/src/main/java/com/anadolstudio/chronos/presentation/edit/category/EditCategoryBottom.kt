package com.anadolstudio.chronos.presentation.edit.category

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.bottom.BaseContentBottom
import com.anadolstudio.chronos.databinding.BottomEditCategoryBinding
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.ui.dialogs.alert.AlertDialogBuilder
import com.anadolstudio.ui.viewbinding.viewBinding
import com.anadolstudio.ui.viewmodel.assistedViewModel
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.utils.animation.AnimateUtil.scaleAnimationOnClick
import com.anadolstudio.utils.util.extentions.getParcelable
import com.anadolstudio.utils.util.extentions.setFragmentResult

open class EditCategoryBottom : BaseContentBottom<EditCategoryState, EditCategoryViewModel, EditCategoryController>(R.layout.bottom_edit_category) {

    companion object {
        private const val RENDER_CATEGORY = "RENDER_SELECTED_CATEGORY"
        private const val RENDER_LOADING = "RENDER_LOADING"
        private const val RENDER_PARENT_GROUP = "RENDER_PARENT_GROUP"
        private const val RENDER_PARENT_HINT = "RENDER_PARENT_HINT"
        private const val RENDER_CHILD_HINT = "RENDER_CHILD_HINT"
    }

    protected val binding by viewBinding { BottomEditCategoryBinding.bind(it) }
    protected val args: EditCategoryBottomArgs by navArgs()

    override fun createViewModelLazy() = assistedViewModel {
        getSharedComponent()
                .editViewModelFactory()
                .create(args.data)
    }

    override fun initView() = with(binding) {
        initFragmentResultListeners(EditCategoryViewModel.CATEGORIES_REQUEST_KEY)
        settingsContainer.categoryNameText.setDrawableEnd(null)
        settingsContainer.categoryNameText.addValidateListener(controller::onNameChanged)
        settingsContainer.parentNameText.setOnIconClickListener(controller::onSearchButtonClicked)
        settingsContainer.parentNameText.addValidateListener(controller::onParentNameChanged)
        editButton.setOnClickListener { controller.onEditClicked() }
        removeButton.setOnClickListener { controller.onRemoveClicked() }
    }

    override fun handleFragmentResult(requestKey: String, data: Bundle) = when (requestKey) {
        EditCategoryViewModel.CATEGORIES_REQUEST_KEY -> controller.onCategoriesSelected(getParcelable(data))
        else -> super.handleFragmentResult(requestKey, data)
    }

    override fun handleEvent(event: SingleEvent) = when (event) {
        is EditCategoryBottomEvents.Result -> setFragmentResult(args.data.requestKey)
        is EditCategoryBottomEvents.ShowConfirmRemoveAlert -> showConfirmRemoveAlert(event.name)
        else -> super.handleEvent(event)
    }

    private fun showConfirmRemoveAlert(name: String) {
        AlertDialogBuilder.buildChoiceDialog(
                context = requireContext(),
                theme = R.style.ChronosDialogAlertTheme,
                title = requireContext().getString(R.string.global_remove),
                message = requireContext().getString(R.string.global_remove_category, name),
                cancelable = true,
                onPositiveButtonClicked = { controller.onConfirmRemoveClicked() }
        )
    }

    override fun render(state: EditCategoryState) = with(state) {
        renderHint(categoryUi.name, categoryUi.parentName)
        renderParentGroup(!categoryUi.isMainCategory)
        binding.settingsContainer.categoryNameText.setText(name, withValidate = false)
        binding.editButton.isEnabled = state.createEnable
        renderSelectedCategory(parentCategoryUi)
        renderLoading(isLoading)
    }

    private fun renderSelectedCategory(parentCategoryUi: CategoryUi?) = parentCategoryUi.render(RENDER_CATEGORY) {
        binding.settingsContainer.parentItem.root.isVisible = this != null

        if (this != null) {
            binding.settingsContainer.parentItem.categoryVew.setup(this)
            binding.settingsContainer.parentNameText.setText(name, withValidate = false)
        }
    }

    private fun renderLoading(isLoading: Boolean) = isLoading.render(RENDER_LOADING) {
        binding.settingsContainer.categoryNameText.isEnabled = !isLoading
        binding.settingsContainer.parentNameText.isEnabled = !isLoading
        binding.editButton.setLoading(isLoading)
        binding.removeButton.setLoading(isLoading)
    }

    private fun renderParentGroup(isVisible: Boolean) = isVisible.render(RENDER_PARENT_GROUP) {
        binding.settingsContainer.parentItem.root.isVisible = isVisible
        binding.settingsContainer.parentNameText.isVisible = isVisible
        binding.removeButton.isVisible = isVisible
    }

    private fun renderHint(childHint: String, parentHint: String?) = childHint.render(
            RENDER_CHILD_HINT,
            RENDER_PARENT_HINT to parentHint
    ) {
        binding.settingsContainer.apply {
            categoryNameText.setSupportHint(getString(R.string.prev_name, childHint))
            parentNameText.setSupportHint(getString(R.string.prev_parent_category, parentHint))
        }
    }

}
