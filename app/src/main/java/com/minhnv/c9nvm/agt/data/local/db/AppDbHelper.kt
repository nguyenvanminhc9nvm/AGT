package com.minhnv.c9nvm.agt.data.local.db

import com.minhnv.c9nvm.agt.data.model.PageComic
import com.minhnv.c9nvm.agt.di.IoDispatcher
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDbHelper @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val appDatabase: AppDatabase
) : DBHelper {
    override suspend fun insertPage(comicId: Int, page: Int) = withContext(ioDispatcher) {
        appDatabase.pageComicDao.insertPage(PageComic(comicId, page))
    }

    override suspend fun getPage(comicId: Int): Int = withContext(ioDispatcher) {
        return@withContext appDatabase.pageComicDao.getPage(comicId).first().page
    }
}
