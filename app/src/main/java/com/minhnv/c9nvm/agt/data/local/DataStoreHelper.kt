package com.minhnv.c9nvm.agt.data.local

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    suspend fun savePage(page: Int)

    fun getPage(): Flow<Int>
}