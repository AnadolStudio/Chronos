package com.anadolstudio.data.repository.subcategory_object

import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectDao
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity
import com.anadolstudio.domain.repository.subcategory_object.SubcategoryObjectRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

class SubcategoryObjectRepositoryImpl(private val subcategoryObjectDao: SubcategoryObjectDao) : SubcategoryObjectRepository {

    override fun getAllObjects(): Single<List<SubcategoryObjectEntity>> = subcategoryObjectDao
            .getAllObjects()

    override fun insertObject(subcategoryObjectEntity: SubcategoryObjectEntity): Completable = subcategoryObjectDao
            .insertObject(subcategoryObjectEntity)

    override fun updateObjects(objectId: UUID, name: String): Single<Int> = subcategoryObjectDao
            .updateObjects(objectId, name)

    override fun getObjectsById(objectId: String): Single<SubcategoryObjectEntity> = subcategoryObjectDao
            .getObjectsById(objectId)

    override fun deleteObjects(subcategory: SubcategoryObjectEntity): Completable = subcategoryObjectDao
            .deleteObjects(subcategory)

}
