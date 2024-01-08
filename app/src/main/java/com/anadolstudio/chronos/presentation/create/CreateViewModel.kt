package com.anadolstudio.chronos.presentation.create

import androidx.core.os.bundleOf
import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.CategoryNavigationArgs
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.core.R.string
import com.anadolstudio.core.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import com.anadolstudio.domain.repository.common.ResourceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CreateViewModel @AssistedInject constructor(
        @Assisted args: CreateNavigationArgs,
        private val resources: ResourceRepository,
        private val chronosRepository: ChronosRepository,
) : BaseContentViewModel<CreateState>(
        initState = CreateState(
                categoryList = args.categoryList,
        )
), CreateController {

    init {
        onNameChanged(args.name)
    }

    override fun onNameChanged(name: String) = updateState { copy(name = name) }

    override fun onParentNameChanged(name: String) = updateState { copy(parentName = name, parentCategoryUi = state.nameCategoryMap[name]) }

    override fun onSearchButtonClicked() = navigateTo(
            id = R.id.action_createBottom_to_categoriesBottom,
            args = bundleOf(
                    resources.getString(string.data) to CategoryNavigationArgs(state.categoryList)
            )
    )

    override fun onCategoriesSelected(categoryUi: CategoryUi?) = updateState { copy(parentCategoryUi = categoryUi) }

    override fun onCreateClicked() {
        val parent = state.parentCategoryUi ?: return
        val subcategory = SubcategoryDomain(
                name = state.name,
                mainCategoryId = parent.mainCategoryId,
                parentCategoryId = parent.id,
        )

        chronosRepository
                .insertSubcategory(subcategory)
                .doOnSubscribe { updateState { copy(isLoading = true) } }
                .smartSubscribe(
                        onComplete = {
                            val category = subcategory
                                    .toCategoryUi(parent.name, parent.color)
                                    .first()
                            // TODO при создании собирамой категории затреканное время
                            //  переносить в новую дочернуюю категорию Unknown
                            showEvent(CreateBottomEvents.Result(category))
                            navigateUp()
                        },
                        onError = this::showError,
                        onFinally = { updateState { copy(isLoading = false) } }
                )
                .disposeOnCleared()
    }

    @AssistedFactory
    interface Factory {
        fun create(args: CreateNavigationArgs): CreateViewModel
    }
}
