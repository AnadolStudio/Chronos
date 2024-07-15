package com.anadolstudio.data.repository.chronos

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_1_2 :Migration(1,2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("UPDATE track_table SET date = 1 + date / (1000 * 60 * 60 * 24)")
    }
}
