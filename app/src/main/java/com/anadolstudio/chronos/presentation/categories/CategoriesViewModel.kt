package com.anadolstudio.chronos.presentation.categories

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CategoriesViewModel @AssistedInject constructor(
        @Assisted categoryList: List<CategoryUi>
) : BaseContentViewModel<CategoriesState>(
        initState = CategoriesState(
                categoryList = categoryList
        )
), CategoriesController {

    override fun onCategoryClicked(categoryUi: CategoryUi) {
        showEvent(CategoriesBottomEvents.Result(categoryUi))
        navigateUp()
    }

    @AssistedFactory
    interface Factory {
        fun create(categoryList: List<CategoryUi>): CategoriesViewModel
    }
}
