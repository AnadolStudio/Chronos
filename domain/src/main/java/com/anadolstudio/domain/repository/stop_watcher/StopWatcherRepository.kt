package com.anadolstudio.domain.repository.stop_watcher

import com.anadolstudio.core.util.data_time.Time
import io.reactivex.Observable

interface StopWatcherRepository {

    val currentDelta: Time?
    var stopWatcherData: StopWatcherData

    fun observeStopWatcherChanges(): Observable<StopWatcherData>

}
