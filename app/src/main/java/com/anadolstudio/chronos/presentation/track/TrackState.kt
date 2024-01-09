package com.anadolstudio.chronos.presentation.track

import com.anadolstudio.chronos.presentation.categories.model.CategoryUi
import com.anadolstudio.chronos.presentation.categories.model.toCategoryUi
import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import org.joda.time.DateTime
import java.util.UUID

data class TrackState(
        val selectedDateTime: DateTime,
        val categoryState: CategoryState,
        val fromStopWatcher: Boolean,
        val selectedCategoryUi: CategoryUi? = null,
        val name: String = "",
        val hours: Int = 0,
        val minutes: Int = 0,
        val isLoading: Boolean = false,
        val lastTrackList: List<CategoryUi> = emptyList()
) {

    val time: Time get() = Time(hours = hours, minutes = minutes, seconds = 0)

    val applyButtonState: ApplyButtonState = ApplyButtonState(
            time = time,
            selectedCategoryUi = selectedCategoryUi,
            name = name
    )
}

data class ApplyButtonState(
        val isEnable: Boolean,
        val hasSelectedCategory: Boolean,
) {
    private companion object {
        const val MIN_TRACK_MINUTES = 10
    }

    constructor(
            selectedCategoryUi: CategoryUi?,
            name: String,
            time: Time,
    ) : this(
            isEnable = time.totalMinutes >= MIN_TRACK_MINUTES && name.isNotBlank(),
            hasSelectedCategory = selectedCategoryUi != null,
    )
}

data class CategoryState(val categoryList: List<CategoryUi>) {

    companion object {
        @JvmName("fromMainCategoryList")
        operator fun invoke(mainCategoryList: List<MainCategoryDomain>) = CategoryState(mainCategoryList.toCategoryUi())
    }

    val nameCategoryMap: Map<String, CategoryUi> = categoryList.associateBy { it.name }
    val idCategoryMap: Map<UUID, CategoryUi> = categoryList.associateBy { it.id }
    val childCategoryList: List<CategoryUi> = categoryList.filter { !it.hasChild }
    val childNameCategoryMap: Map<String, CategoryUi> = nameCategoryMap.filter { (_, category) -> !category.hasChild }
}
