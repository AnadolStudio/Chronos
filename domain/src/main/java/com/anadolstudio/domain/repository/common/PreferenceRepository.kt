package com.anadolstudio.domain.repository.common

import org.joda.time.DateTime

interface PreferenceRepository {
    var lastSelectedDate: DateTime
}
