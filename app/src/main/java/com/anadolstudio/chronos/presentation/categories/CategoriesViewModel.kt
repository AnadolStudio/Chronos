package com.anadolstudio.chronos.presentation.categories

import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.CategoriesBottomEvents.Result
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
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

    override fun onCategoryClicked(categoryUi: CategoryUi) = navigateUpWithResult(Result(categoryUi))

    @AssistedFactory
    interface Factory {
        fun create(categoryList: List<CategoryUi>): CategoriesViewModel
    }
}
