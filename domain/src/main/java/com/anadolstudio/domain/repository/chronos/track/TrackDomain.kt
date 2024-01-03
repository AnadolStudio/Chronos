package com.anadolstudio.domain.repository.chronos.track

import org.joda.time.DateTime
import java.util.UUID

data class TrackDomain(
    val id: Int = 0,
    val uuid: UUID = UUID.randomUUID(),
    val subcategoryId: UUID,
    val date: DateTime,
    val millis: Long,
    val fromStopWatcher: Boolean = false,
)
