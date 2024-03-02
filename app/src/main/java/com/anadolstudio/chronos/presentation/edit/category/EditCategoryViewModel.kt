package com.anadolstudio.chronos.presentation.edit.category

import androidx.exifinterface.media.ExifInterface.IfdType
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.CategoryNavigationArgs
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.common.CategoryState
import com.anadolstudio.utils.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.common.ResourceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Completable
import java.util.UUID

class EditCategoryViewModel @AssistedInject constructor(
        @Assisted args: EditCategoryNavigationArgs,
        private val resources: ResourceRepository,
        private val chronosRepository: ChronosRepository,
) : BaseContentViewModel<EditCategoryState>(
        initState = EditCategoryState(
                categoryState = CategoryState(args.categoryList),
                categoryUi = args.selectedCategory,
                oldName = args.selectedCategory.name
        )
), EditCategoryController {

    companion object {
        const val CATEGORIES_REQUEST_KEY = "1_000_004"
    }

    override fun onNameChanged(name: String) = updateState { copy(name = name) }

    override fun onParentNameChanged(name: String) = updateState {
        copy(parentName = name, parentCategoryUi = state.categoryState.nameCategoryMap[name])
    }

    override fun onSearchButtonClicked() = navigateTo(
            id = R.id.action_editBottom_to_categoriesBottom,
            args = resources.navigateArg(
                    CategoryNavigationArgs(
                            requestKey = CATEGORIES_REQUEST_KEY,
                            categoryList = getValidCategories()
                    )
            )
    )

    private fun getValidCategories() :List<CategoryUi> {
        val childSet = mutableSetOf<UUID>()

        return state.categoryState.categoryList.filter {
            val isNotParent = it.id != state.categoryUi.parentCategoryId
            val isNotChild = it.parentCategoryId != state.categoryUi.id

            if (!isNotChild) {
                childSet.add(it.id)
            }
            val isNotChildOfChild = !childSet.contains(it.parentCategoryId)

            it.id != state.categoryUi.id && isNotParent && isNotChild && isNotChildOfChild
        }
    }

    override fun onCategoriesSelected(categoryUi: CategoryUi?) = updateState { copy(parentCategoryUi = categoryUi) }

    override fun onEditClicked() {
        if (state.isLoading) return

        val completable = if (state.categoryUi.isMainCategory) {
            chronosRepository
                    .getMainCategoryById(state.categoryUi.id)
                    .flatMapCompletable { chronosRepository.updateMainCategory(it.copy(name = state.name)) }
        } else {
            val parentCategoryUi = state.parentCategoryUi ?: return

            chronosRepository
                    .getSubcategoryById(state.categoryUi.id)
                    .flatMapCompletable {
                        chronosRepository.updateSubcategory(
                                it.copy(
                                        name = state.name,
                                        parentCategoryId = parentCategoryUi.id,
                                        mainCategoryId = parentCategoryUi.mainCategoryId
                                )
                        )
                    }
        }

        completable.smartSubscribe(
                onSubscribe = { updateState { copy(isLoading = true) } },
                onComplete = { navigateUpWithResult(EditCategoryBottomEvents.Result) },
                onError = this::showError,
                onFinally = { updateState { copy(isLoading = false) } },
        ).disposeOnCleared()
    }

    override fun onRemoveClicked() {
        if (state.isLoading) return

        showEvent(EditCategoryBottomEvents.ShowConfirmRemoveAlert(state.categoryUi.name))
    }

    override fun onConfirmRemoveClicked() {
        Completable.concatArray(
                chronosRepository.deleteSubcategoryById(state.categoryUi.id),
                chronosRepository.deleteTrackByCategoryId(state.categoryUi.id)
        )
                .smartSubscribe(
                        onSubscribe = { updateState { copy(isLoading = true) } },
                        onComplete = { navigateUpWithResult(EditCategoryBottomEvents.Result) },
                        onError = this::showError,
                        onFinally = { updateState { copy(isLoading = false) } },
                )
                .disposeOnCleared()
    }

    @AssistedFactory
    interface Factory {
        fun create(args: EditCategoryNavigationArgs): EditCategoryViewModel
    }
}
