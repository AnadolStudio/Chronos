package com.anadolstudio.data.repository

import com.anadolstudio.data.data.Category

interface CategoryRepository {

    fun addCategory(categoryId: String, name: String, minuteCount: Int, parent: String? = null): Category

    fun setCategory(categoryId: String, name: String, minuteCount: Int, parent: String? = null): Category

    fun renameCategory(categoryId: String, newName: String): Category

    fun getAllCategories():List<Category>

    fun getCategory(categoryId: String): Category

}
