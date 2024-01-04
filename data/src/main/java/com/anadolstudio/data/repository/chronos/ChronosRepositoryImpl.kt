package com.anadolstudio.data.repository.chronos

import com.anadolstudio.core.util.rx.schedulersIoToMain
import com.anadolstudio.data.repository.chronos.main_category.MainCategoryDao
import com.anadolstudio.data.repository.chronos.main_category.toDomain
import com.anadolstudio.data.repository.chronos.main_category.toEntity
import com.anadolstudio.data.repository.chronos.subcategory.SubcategoryDao
import com.anadolstudio.data.repository.chronos.subcategory.SubcategoryEntity
import com.anadolstudio.data.repository.chronos.subcategory.toDomain
import com.anadolstudio.data.repository.chronos.subcategory.toEntity
import com.anadolstudio.data.repository.chronos.track.TrackDao
import com.anadolstudio.data.repository.chronos.track.toDomain
import com.anadolstudio.data.repository.chronos.track.toEntity
import com.anadolstudio.domain.repository.chronos.ChronosRepository
import com.anadolstudio.domain.repository.chronos.main_category.MainCategoryDomain
import com.anadolstudio.domain.repository.chronos.subcategory.SubcategoryDomain
import com.anadolstudio.domain.repository.chronos.track.TrackDomain
import io.reactivex.Completable
import io.reactivex.Single
import org.joda.time.DateTime
import java.util.UUID

class ChronosRepositoryImpl(
    private val mainCategoryDao: MainCategoryDao,
    private val subcategoryDao: SubcategoryDao,
    private val trackDao: TrackDao
) : ChronosRepository {

    // TODO Need Test
    override fun getAllMainCategories(): Single<List<MainCategoryDomain>> = Single.zip(
        mainCategoryDao.getAllMainCategories(),
        getAllSubcategories().map { subcategories -> subcategories.groupBy { it.mainCategoryId } }
    ) { mainCategoryList, subcategoryMap ->
        mainCategoryList.map { it.toDomain(subcategoryMap[it.uuid].orEmpty()) }
    }
        .schedulersIoToMain()

    override fun insertMainCategory(category: MainCategoryDomain): Completable = mainCategoryDao
        .insertMainCategory(category.toEntity())
        .schedulersIoToMain()

    override fun updateMainCategory(category: MainCategoryDomain): Completable = mainCategoryDao
        .updateMainCategory(category.toEntity())
        .schedulersIoToMain()

    override fun deleteMainCategory(category: MainCategoryDomain): Completable = mainCategoryDao
        .deleteMainCategory(category.toEntity())
        .schedulersIoToMain()

    // TODO Need Test
    override fun getAllSubcategories(): Single<List<SubcategoryDomain>> = subcategoryDao
        .getAllSubcategories()
        .map { subcategories ->
            val categoriesMap = subcategories.groupBy { it.parentCategoryId }
            val rootCategories = subcategories
                .filter { it.isRoot }
                .map { it.toDomain(findInnerSubcategories(it, categoriesMap)) }

            return@map rootCategories
        }
        .schedulersIoToMain()

    private fun findInnerSubcategories(
        subcategory: SubcategoryEntity,
        categoriesMap: Map<UUID, List<SubcategoryEntity>>
    ): List<SubcategoryDomain> {
        val innerSubcategoriesEntity = categoriesMap[subcategory.uuid].orEmpty()

        val innerSubcategories = innerSubcategoriesEntity.flatMap {
            findInnerSubcategories(it, categoriesMap)
        }

        return listOf(subcategory.toDomain(innerSubcategories))
    }

    override fun insertSubcategory(subcategory: SubcategoryDomain): Completable = subcategoryDao
        .insertSubcategory(subcategory.toEntity())
        .schedulersIoToMain()

    override fun updateSubcategory(subcategory: SubcategoryDomain): Completable = subcategoryDao
        .updateSubcategory(subcategory.toEntity())
        .schedulersIoToMain()

    override fun deleteSubcategory(subcategory: SubcategoryDomain): Completable = subcategoryDao
        .deleteSubcategory(subcategory.toEntity())
        .schedulersIoToMain()

    override fun getAllTracks(): Single<List<TrackDomain>> = trackDao
        .getAllTracks()
        .map { trackList -> trackList.toDomain() }
        .schedulersIoToMain()

    override fun getAllTracksFromStopWatcher(): Single<List<TrackDomain>> = trackDao
        .getAllTracksFromStopWatcher()
        .map { it.toDomain() }
        .schedulersIoToMain()

    override fun insertTrack(trackDomain: TrackDomain): Completable = trackDao
        .insertTrack(trackDomain.toEntity())
        .schedulersIoToMain()

    override fun updateTrack(
            trackDomain: TrackDomain
    ): Completable = trackDao
        .updateTrack(trackDomain.uuid, trackDomain.date, trackDomain.minutes, trackDomain.subcategoryId)
        .schedulersIoToMain()

    override fun getTrackById(trackId: UUID): Single<TrackDomain> = trackDao
        .getTrackById(trackId)
        .map { it.toDomain() }
        .schedulersIoToMain()

    override fun getTrackListByDate(date: DateTime): Single<List<TrackDomain>> = trackDao
        .getTrackListByDate(date)
        .map { it.toDomain() }
        .schedulersIoToMain()

    override fun getLastTrackList(limit: Int): Single<List<TrackDomain>> = trackDao
            .getTrackList(limit)
            .map { it.toDomain() }
            .schedulersIoToMain()

    override fun deleteTrack(trackDomain: TrackDomain): Completable = trackDao
        .deleteTrack(trackDomain.toEntity())
        .schedulersIoToMain()
}
