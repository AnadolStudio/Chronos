package com.anadolstudio.data.repository

import com.anadolstudio.domain.repository.category.CategoryRepository
import com.anadolstudio.domain.repository.subcategory.SubcategoryRepository
import com.anadolstudio.domain.repository.subcategory_object.SubcategoryObjectRepository
import com.anadolstudio.domain.repository.task.TaskRepository

class DataSource constructor(
        categoryRepository: CategoryRepository,
        subcategoryRepository: SubcategoryRepository,
        subcategoryObjectRepository: SubcategoryObjectRepository,
        taskRepository: TaskRepository
) : CategoryRepository by categoryRepository,
        SubcategoryRepository by subcategoryRepository,
        SubcategoryObjectRepository by subcategoryObjectRepository,
        TaskRepository by taskRepository
