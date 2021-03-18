package com.minhnv.c9nvm.agt.di.module

import android.app.Application
import android.content.Context
import com.minhnv.c9nvm.agt.data.AppDataManager
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.data.local.AppDataStoreHelper
import com.minhnv.c9nvm.agt.data.local.DataStoreHelper
import com.minhnv.c9nvm.agt.data.remote.ApiService
import com.minhnv.c9nvm.agt.data.remote.AppApiHelper
import com.minhnv.c9nvm.agt.di.DefaultDispatcher
import com.minhnv.c9nvm.agt.di.IoDispatcher
import com.minhnv.c9nvm.agt.di.MainDispatcher
import com.minhnv.c9nvm.agt.utils.rx.AppSchedulerProvider
import com.minhnv.c9nvm.agt.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun providerAppApiHelper(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ApiService {
        return AppApiHelper(ioDispatcher)
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
    fun providerDataManager(apiHelper: ApiService, dataStoreHelper: DataStoreHelper): DataManager {
        return AppDataManager(apiHelper, dataStoreHelper)
    }

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

}