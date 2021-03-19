package com.minhnv.c9nvm.agt.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PageComic")
data class PageComic(
    @PrimaryKey
    @ColumnInfo(name = "comicId")
    val comicId: Int,
    @ColumnInfo(name = "page")
    val page: Int
)
