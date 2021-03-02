package com.minhnv.c9nvm.agt.data.local

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppDataStoreHelper @Inject constructor(
    private val context: Context
) : DataStoreHelper {
}