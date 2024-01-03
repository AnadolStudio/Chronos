package com.anadolstudio.data.repository.chronos.track

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anadolstudio.data.repository.chronos.track.TrackEntity.Companion.TRACK_TABLE
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.UUID

@Dao
interface TrackDao {

    @Query("SELECT * FROM $TRACK_TABLE")
    fun getAllTracks(): Single<List<TrackEntity>>

    @Query("SELECT * FROM $TRACK_TABLE WHERE from_stop_watcher = 1")
    fun getAllTracksFromStopWatcher(): Single<List<TrackEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTrack(trackDomain: TrackEntity): Completable

    @Query("UPDATE $TRACK_TABLE SET millis = :milliseconds AND subcategory_id = :subcategoryId WHERE uuid = :trackId AND date = :dateTime")
    fun updateTrack(trackId: UUID, dateTime: DateTime, milliseconds: Long, subcategoryId: UUID): Single<Int>

    @Query("SELECT * FROM $TRACK_TABLE WHERE uuid = :trackId")
    fun getTrackById(trackId: UUID): Single<TrackEntity>

    @Query("SELECT * FROM $TRACK_TABLE WHERE date = :dateTime")
    fun getTrackListByDate(dateTime: DateTime): Single<List<TrackEntity>>

    @Delete
    fun deleteTrack(trackEntity: TrackEntity): Completable

}
