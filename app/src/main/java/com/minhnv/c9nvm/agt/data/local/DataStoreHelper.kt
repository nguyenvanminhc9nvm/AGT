package com.minhnv.c9nvm.agt.data.local

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    suspend fun savePageWithComicId(comicId: Int, page: Int)

    fun getPageWithComicId(comicId: Int): Flow<Int>
}