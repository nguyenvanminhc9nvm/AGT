package com.minhnv.c9nvm.agt.data.local

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {
    suspend fun saveFirstTimeSeeAdMob(isFirst: Boolean)

    val isFirstTimeSeeAdMob: Flow<Boolean>
}