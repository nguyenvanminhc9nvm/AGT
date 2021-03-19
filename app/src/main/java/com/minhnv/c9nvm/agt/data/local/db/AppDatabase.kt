package com.minhnv.c9nvm.agt.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minhnv.c9nvm.agt.data.local.db.dao.PageComicDao
import com.minhnv.c9nvm.agt.data.model.PageComic

@Database(entities = [PageComic::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val pageComicDao: PageComicDao
}