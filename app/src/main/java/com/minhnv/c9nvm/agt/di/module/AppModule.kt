package com.minhnv.c9nvm.agt.di.module

import android.content.Context
import com.minhnv.c9nvm.agt.data.AppDataManager
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.data.local.AppDataStoreHelper
import com.minhnv.c9nvm.agt.data.local.DataStoreHelper
import com.minhnv.c9nvm.agt.data.remote.ApiService
import com.minhnv.c9nvm.agt.data.remote.AppApiHelper
import com.minhnv.c9nvm.agt.utils.rx.AppSchedulerProvider
import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providerAppApiHelper(): ApiService {
        return AppApiHelper()
    }

    @Provides
    fun providerSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun providerAppDataStore(context: Context): DataStoreHelper {
        return AppDataStoreHelper(context)
    }

    @Provides
    @Singleton
    fun providerDataManager(apiHelper: ApiService): DataManager {
        return AppDataManager(apiHelper)
    }

}