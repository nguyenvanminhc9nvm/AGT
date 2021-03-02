package com.minhnv.c9nvm.agt.data

import com.minhnv.c9nvm.agt.data.local.DataStoreHelper
import com.minhnv.c9nvm.agt.data.remote.ApiHelper

interface DataManager: DataStoreHelper, ApiHelper {
}