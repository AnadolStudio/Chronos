package com.anadolstudio.data.repository.chronos.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anadolstudio.domain.repository.chronos.track.TrackDomain
import org.joda.time.DateTime
import java.util.UUID

@Entity(tableName = TrackEntity.TRACK_TABLE)
data class TrackEntity(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "uuid") val uuid: UUID,
        @ColumnInfo(name = "subcategory_id") val subcategoryId: UUID,
        @ColumnInfo(name = "date") val date: DateTime,
        @ColumnInfo(name = "minutes") val minutes: Int,
        @ColumnInfo(name = "from_stop_watcher") val fromStopWatcher: Boolean,
) {
    companion object {
        const val TRACK_TABLE = "track_table"
    }
}

fun TrackEntity.toDomain(): TrackDomain = TrackDomain(
        id = id,
        uuid = uuid,
        subcategoryId = subcategoryId,
        date = date,
        minutes = minutes,
        fromStopWatcher = fromStopWatcher,
        isNew = false
)

fun List<TrackEntity>.toDomain(): List<TrackDomain> = this.map { it.toDomain() }

fun TrackDomain.toEntity(): TrackEntity = TrackEntity(
        id = id,
        uuid = uuid,
        subcategoryId = subcategoryId,
        date = date,
        minutes = minutes,
        fromStopWatcher = fromStopWatcher
)