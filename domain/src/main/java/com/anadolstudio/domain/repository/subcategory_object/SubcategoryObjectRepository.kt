package com.anadolstudio.domain.repository.subcategory_object

import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

interface SubcategoryObjectRepository {

    fun getAllObjects(): Single<List<SubcategoryObjectEntity>>

    fun insertObject(subcategoryObjectEntity: SubcategoryObjectEntity): Completable

    fun updateObjects(objectId: UUID, name: String): Single<Int>

    fun getObjectsById(objectId: String): Single<SubcategoryObjectEntity>

    fun deleteObjects(subcategory: SubcategoryObjectEntity): Completable
}
