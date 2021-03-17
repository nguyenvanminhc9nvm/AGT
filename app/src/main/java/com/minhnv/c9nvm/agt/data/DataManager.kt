package com.minhnv.c9nvm.agt.data

import com.minhnv.c9nvm.agt.data.local.DataStoreHelper
import com.minhnv.c9nvm.agt.data.remote.ApiService

interface DataManager: DataStoreHelper, ApiService

