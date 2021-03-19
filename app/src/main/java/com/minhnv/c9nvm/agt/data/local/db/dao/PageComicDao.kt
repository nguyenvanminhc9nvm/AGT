package com.minhnv.c9nvm.agt.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.minhnv.c9nvm.agt.data.model.PageComic

@Dao
interface PageComicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(pageComic: PageComic)

    @Query("SELECT * FROM PageComic where comicId =:comicId")
    suspend fun getPage(comicId: Int): MutableList<PageComic>
}