package com.anadolstudio.chronos.ui.settings.inner_fragment

import com.anadolstudio.domain.models.settings.SettingsCategoryModel

data class SettingsInnerContent(
        val isEmpty: Boolean,
        val category: SettingsCategoryModel.Category
)
