package com.minhnv.c9nvm.agt.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.minhnv.c9nvm.agt.data.AppDataManager
import com.minhnv.c9nvm.agt.data.DataManager
import com.minhnv.c9nvm.agt.data.local.AppDataStoreHelper
import com.minhnv.c9nvm.agt.data.local.DataStoreHelper
import com.minhnv.c9nvm.agt.data.local.db.AppDatabase
import com.minhnv.c9nvm.agt.data.local.db.AppDbHelper
import com.minhnv.c9nvm.agt.data.local.db.DBHelper
import com.minhnv.c9nvm.agt.data.remote.ApiService
import com.minhnv.c9nvm.agt.data.remote.AppApiHelper
import com.minhnv.c9nvm.agt.di.Databases
import com.minhnv.c9nvm.agt.di.DefaultDispatcher
import com.minhnv.c9nvm.agt.di.IoDispatcher
import com.minhnv.c9nvm.agt.di.MainDispatcher
import com.minhnv.c9nvm.agt.utils.AGTConstant
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
    fun providerDataManager(
        apiHelper: ApiService,
        dataStoreHelper: DataStoreHelper,
        dbHelper: DBHelper
    ): DataManager {
        return AppDataManager(apiHelper, dataStoreHelper, dbHelper)
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

    @Databases
    @Provides
    fun provideDatabaseName() = AGTConstant.DB_NAME

    @Provides
    @Singleton
    fun providerAppDatabase(@Databases dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName).build()
    }

    @Provides
    @Singleton
    fun providerDBHelper(appDbHelper: AppDbHelper): DBHelper {
        return appDbHelper
    }

}