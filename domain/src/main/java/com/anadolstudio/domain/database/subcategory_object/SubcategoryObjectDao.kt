package com.anadolstudio.domain.database.subcategory_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anadolstudio.domain.model.subcategory_object.SubcategoryObjectEntity
import com.anadolstudio.domain.model.subcategory_object.SubcategoryObjectEntity.Companion.SUBCATEGORY_OBJECT_TABLE
import java.util.UUID

@Dao
interface SubcategoryObjectDao {

    @Query("SELECT * FROM $SUBCATEGORY_OBJECT_TABLE")
    fun getAllObjects(): List<SubcategoryObjectEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertObjects(subcategoryObjectEntity: SubcategoryObjectEntity)

    @Query("UPDATE $SUBCATEGORY_OBJECT_TABLE SET name = :name WHERE object_id = :objectId")
    fun updateObjects(objectId: UUID, name: String): Int

    @Query("SELECT * FROM $SUBCATEGORY_OBJECT_TABLE WHERE object_id = :objectId")
    fun getObjectsById(objectId: String): SubcategoryObjectEntity

    @Delete
    fun deleteObjects(subcategory: SubcategoryObjectEntity)

}
