package com.minhnv.c9nvm.agt.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.minhnv.c9nvm.agt.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataStoreHelper @Inject constructor(
    private val context: Context
) : DataStoreHelper {
    private val isFirstTimeLoginApp = booleanPreferencesKey("IsFirst")

    override suspend fun saveFirstTimeSeeAdMob(isFirst: Boolean) {
        context.dataStore.edit {
            it[isFirstTimeLoginApp] = isFirst
        }
    }

    override val isFirstTimeSeeAdMob: Flow<Boolean>
        get() = context.dataStore.data.map {
            it[isFirstTimeLoginApp] ?: false
        }

}