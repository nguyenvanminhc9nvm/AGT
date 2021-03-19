package com.minhnv.c9nvm.agt.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.mutablePreferencesOf
import com.minhnv.c9nvm.agt.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataStoreHelper @Inject constructor(
    private val context: Context
) : DataStoreHelper {
    private val comicIdSave = intPreferencesKey("COMIC_ID")
    private val mutableMapPage = mutablePreferencesOf()

    override suspend fun savePageWithComicId(comicId: Int, page: Int) {
        context.dataStore.edit {
            it[comicIdSave] = comicId
            mutableMapPage.plusAssign(comicIdSave to page)
        }
    }

    override fun getPageWithComicId(comicId: Int): Flow<Int> {
        return context.dataStore.data.map {
            mutableMapPage[comicIdSave] ?: 0
        }
    }
}