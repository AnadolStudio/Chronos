package com.anadolstudio.data.repository.stop_watcher

import android.util.Log
import com.anadolstudio.core.util.data_time.Time
import com.anadolstudio.data.repository.common.PreferencesStorage
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherData
import com.anadolstudio.domain.repository.stop_watcher.StopWatcherRepository
import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor

class StopWatcherRepositoryImpl(private val preferencesStorage: PreferencesStorage) : StopWatcherRepository {

    init {
        Log.d("D_TAG", "StopWatcherRepositoryImpl")
    }

    private val stopWatcherChanges: BehaviorProcessor<StopWatcherData> = BehaviorProcessor.createDefault(stopWatcherData)

    override val currentDelta: Time?
        get() {
            val startTime = stopWatcherData.startTime ?: return null

            return stopWatcherData.deltaTime ?: Time(System.currentTimeMillis() - startTime)
        }

    override var stopWatcherData: StopWatcherData
        set(value) {
            preferencesStorage.stopWatcherData = value
            stopWatcherChanges.onNext(value)
        }
        get() = preferencesStorage.stopWatcherData

    override fun observeStopWatcherChanges(): Observable<StopWatcherData> = stopWatcherChanges.toObservable()

}
