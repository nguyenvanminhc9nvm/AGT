package com.minhnv.c9nvm.agt.data

import com.minhnv.c9nvm.agt.data.model.Humor
import com.minhnv.c9nvm.agt.data.remote.ApiHelper
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataManager @Inject constructor(
    private val apiHelper: ApiHelper
): DataManager {
    override fun getListHumor(page: Int): Observable<List<Humor>> {
        return apiHelper.getListHumor(page)
    }
}