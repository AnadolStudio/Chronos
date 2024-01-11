package com.anadolstudio.data.repository.common

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.anadolstudio.core.R
import com.anadolstudio.domain.repository.common.ResourceRepository

class ResourceRepositoryImpl(private val context: Context) : ResourceRepository {
    override fun getString(id: Int): String = context.getString(id)
    override fun getColor(id: Int): Int = context.getColor(id)
    override fun navigateArg(data: Any): Bundle = bundleOf(getString(R.string.data) to data)
}
