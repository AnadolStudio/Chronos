package com.anadolstudio.chronos.presentation.create

import com.anadolstudio.chronos.R
import com.anadolstudio.chronos.base.viewmodel.BaseContentViewModel
import com.anadolstudio.chronos.presentation.categories.CategoryNavigationArgs
import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.utils.util.rx.smartSubscribe
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import com.anadolstudio.domain.repository.common.ResourceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Completable

class CreateViewModel @AssistedInject constructor(
        @Assisted args: CreateNavigationArgs,
        private val resources: ResourceRepository,
        private val chronosRepository: ChronosRepository,
) : BaseContentViewModel<CreateState>(
        initState = CreateState(
                categoryList = args.categoryList,
        )
), CreateController {

    companion object {
        const val CATEGORIES_REQUEST_KEY = "1_000_003"
    }

    init {
        onNameChanged(args.name)
    }

    override fun onNameChanged(name: String) = updateState { copy(name = name) }

    override fun onParentNameChanged(name: String) = updateState { copy(parentName = name, parentCategoryUi = state.nameCategoryMap[name]) }

    override fun onSearchButtonClicked() = navigateTo(
            id = R.id.action_createBottom_to_categoriesBottom,
            args = resources.navigateArg(
                    CategoryNavigationArgs(
                            requestKey = CATEGORIES_REQUEST_KEY,
                            categoryList = state.categoryList
                    )
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

        val unknownSubcategory = SubcategoryDomain(
                name = resources.getString(R.string.unknown_category),
                mainCategoryId = parent.mainCategoryId,
                parentCategoryId = parent.id,
        )

        val transferTrackCompletable = chronosRepository.getTrackByCategoryId(parent.id)
                .flatMapCompletable { trackList ->
                    if (parent.hasChild || trackList.isEmpty()) {
                        Completable.complete()
                    } else {
                        val updateTrackCompletableList = trackList.map {
                            chronosRepository.updateTrack(it.copy(subcategoryId = unknownSubcategory.uuid))
                        }
                        val updateOldTracks = Completable.concatArray(*updateTrackCompletableList.toTypedArray())
                        Completable.concatArray(chronosRepository.insertSubcategory(unknownSubcategory), updateOldTracks)
                    }
                }

        Completable.concatArray(
                chronosRepository.insertSubcategory(subcategory),
                transferTrackCompletable,
        ).smartSubscribe(
                onSubscribe = { updateState { copy(isLoading = true) } },
                onComplete = {
                    val category = subcategory
                            .toCategoryUi(parent.name, parent.color)
                            .first()

                    // TODO при создании собирамой категории затреканное время
                    //  переносить в новую дочернуюю категорию Unknown
                    navigateUpWithResult(CreateBottomEvents.Result(category))
                },
                onError = this::showError,
                onFinally = { updateState { copy(isLoading = false) } }
        ).disposeOnCleared()
    }

    @AssistedFactory
    interface Factory {
        fun create(args: CreateNavigationArgs): CreateViewModel
    }
}
