package com.anadolstudio.data.repository

import com.anadolstudio.data.data.TimeOperation

interface TimeOperationsRepository {

    fun addTimeOperation(
        idOperation: Int,
        day: Int,
        categoryId: String,
        minuteCount: Int,
        description: String?
    ): TimeOperation

    fun setTimeOperation(
        idOperation: Int,
        day: Int,
        categoryId: String,
        minuteCount: Int,
        description: String?
    ): TimeOperation

    fun removeTimeOperation(idOperation: Int): TimeOperation

    fun getTimeOperationsByDay(day: Int): List<TimeOperation>

    fun getTimeOperationsByPeriod(startDaY: Int, endDay: Int): List<TimeOperation>

    fun getTimeOperationsByMonth(month: Int): List<TimeOperation>

    fun getTimeOperationsByYear(year: Int): List<TimeOperation>

}
