package com.anadolstudio.data.repository

import com.anadolstudio.data.data.Category

interface CategoryRepository {

    // TODO Подумай лучше

    fun insertCategory(category: Category)

    fun updateCategory(category: Category)

    fun renameCategory(categoryId: String, newName: String): Category

    fun getAllCategories(): List<Category>

    fun getCategory(categoryId: String): Category

    fun deleteCategory(category: Category)

}
