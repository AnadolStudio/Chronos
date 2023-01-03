package com.anadolstudio.data.data

data class TimeOperation(
    val operationId: String,
    val day: Int,
    val categoryId: String,
    val description: String?,
    val minuteCont: Int
)
