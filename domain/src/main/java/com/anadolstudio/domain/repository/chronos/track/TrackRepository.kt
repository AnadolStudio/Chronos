package com.anadolstudio.domain.repository.chronos.track

import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.UUID

interface TrackRepository {

    fun getAllTracks(): Single<List<TrackDomain>>

    fun getAllTracksFromStopWatcher(): Single<List<TrackDomain>>

    fun insertTrack(trackDomain: TrackDomain): Completable

    fun updateTrack(trackId: UUID, dateTime: DateTime, milliseconds: Long, subcategoryId: UUID): Single<Int>

    fun getTrackById(trackId: UUID): Single<TrackDomain>

    fun getTrackListByDate(date: DateTime): Single<List<TrackDomain>>

    fun deleteTrack(trackDomain: TrackDomain): Completable
}
