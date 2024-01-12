package com.anadolstudio.domain.repository.chronos.track

import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.UUID

interface TrackRepository {

    fun getAllTracks(): Single<List<TrackDomain>>

    fun getAllTracksFromStopWatcher(): Single<List<TrackDomain>>

    fun insertTrack(trackDomain: TrackDomain): Completable

    fun updateTrack(trackDomain: TrackDomain): Completable

    fun updateTrackTimeById(id: UUID, totalMinutes: Int): Completable

    fun getTrackById(trackId: UUID): Single<TrackDomain>

    fun getTrackByCategoryId(trackId: UUID): Single<List<TrackDomain>>

    fun getTrackListByDate(date: DateTime): Single<List<TrackDomain>>

    fun getLastTrackList(limit: Int): Single<List<TrackDomain>>

    fun deleteTrackByCategoryId(id: UUID): Completable

    fun deleteTrackById(id: UUID): Completable
}
