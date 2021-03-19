package com.minhnv.c9nvm.agt.data.local.db

interface DBHelper {
    suspend fun insertPage(comicId: Int, page: Int)

    suspend fun getPage(comicId: Int): Int
}