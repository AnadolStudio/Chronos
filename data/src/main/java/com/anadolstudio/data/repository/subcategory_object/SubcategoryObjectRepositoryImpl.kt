package com.anadolstudio.data.repository.subcategory_object

import com.anadolstudio.core.util.rx.schedulersIoToMain
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectDao
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity
import com.anadolstudio.domain.repository.subcategory_object.SubcategoryObjectRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

class SubcategoryObjectRepositoryImpl(private val subcategoryObjectDao: SubcategoryObjectDao) : SubcategoryObjectRepository {

    override fun getAllObjects(): Single<List<SubcategoryObjectEntity>> = subcategoryObjectDao
            .getAllObjects()
            .schedulersIoToMain()

    override fun insertObjects(subcategoryObjectEntity: SubcategoryObjectEntity): Completable = subcategoryObjectDao
            .insertObjects(subcategoryObjectEntity)
            .schedulersIoToMain()

    override fun updateObjects(objectId: UUID, name: String): Single<Int> = subcategoryObjectDao
            .updateObjects(objectId, name)
            .schedulersIoToMain()

    override fun getObjectsById(objectId: String): Single<SubcategoryObjectEntity> = subcategoryObjectDao
            .getObjectsById(objectId)
            .schedulersIoToMain()

    override fun deleteObjects(subcategory: SubcategoryObjectEntity): Completable = subcategoryObjectDao
            .deleteObjects(subcategory)
            .schedulersIoToMain()

}
