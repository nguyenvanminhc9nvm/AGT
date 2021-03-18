package com.minhnv.c9nvm.agt.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.minhnv.c9nvm.agt.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataStoreHelper @Inject constructor(
    private val context: Context
) : DataStoreHelper {

    companion object {
        val KEY_PAGE = intPreferencesKey("KEY_PAGE")
    }

    override suspend fun savePage(page: Int) {
        context.dataStore.edit {  mutablePreferences ->
            mutablePreferences[KEY_PAGE] = page
        }
    }

    override fun getPage(): Flow<Int> {
        return context.dataStore.data.map {
            it[KEY_PAGE] ?: 0
        }
    }
}