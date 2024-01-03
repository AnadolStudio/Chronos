package com.anadolstudio.domain.repository.chronos

import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryRepository
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryRepository
import com.anadolstudio.domain.repository.chronos.track.TrackRepository

interface ChronosRepository : MainCategoryRepository, SubcategoryRepository, TrackRepository
