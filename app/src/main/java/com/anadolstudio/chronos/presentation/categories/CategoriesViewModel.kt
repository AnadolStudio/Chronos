package com.anadolstudio.chronos.presentation.categories

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CategoriesViewModel @AssistedInject constructor(
        @Assisted mainCategories: List<MainCategoryDomain>
) : BaseContentViewModel<CategoriesState>(
        initState = CategoriesState(
                categoryList = mainCategories.flatMap { it.toCategoryUi() }
        )
), CategoriesController {

    override fun onCategoryClicked(categoryUi: CategoryUi) {
        showEvent(CategoriesBottomEvents.Result(categoryUi))
        navigateUp()
    }

    @AssistedFactory
    interface Factory {
        fun create(mainCategories: List<MainCategoryDomain>): CategoriesViewModel
    }
}
