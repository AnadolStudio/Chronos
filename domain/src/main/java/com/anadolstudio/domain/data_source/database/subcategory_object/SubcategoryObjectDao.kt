package com.anadolstudio.domain.data_source.database.subcategory_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anadolstudio.domain.data_source.database.subcategory_object.SubcategoryObjectEntity.Companion.SUBCATEGORY_OBJECT_TABLE
import io.reactivex.Completable
import io.reactivex.Single
import java.util.UUID

@Dao
interface SubcategoryObjectDao {

    @Query("SELECT * FROM $SUBCATEGORY_OBJECT_TABLE")
    fun getAllObjects(): Single<List<SubcategoryObjectEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertObject(subcategoryObjectEntity: SubcategoryObjectEntity): Completable

    @Query("UPDATE $SUBCATEGORY_OBJECT_TABLE SET name = :name WHERE object_id = :objectId")
    fun updateObjects(objectId: UUID, name: String): Single<Int>

    @Query("SELECT * FROM $SUBCATEGORY_OBJECT_TABLE WHERE object_id = :objectId")
    fun getObjectsById(objectId: String): Single<SubcategoryObjectEntity>

    @Delete
    fun deleteObjects(subcategory: SubcategoryObjectEntity): Completable

}
