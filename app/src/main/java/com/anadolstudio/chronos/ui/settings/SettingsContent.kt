package com.anadolstudio.chronos.ui.settings

import com.anadolstudio.domain.models.settings.SettingsCategoryModel

data class SettingsContent(
        val categoriesList: List<SettingsCategoryModel.Category>
)
