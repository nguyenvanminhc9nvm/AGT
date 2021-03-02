package com.minhnv.c9nvm.agt.di.builder

import com.minhnv.c9nvm.agt.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    abstract fun mainActivity(): MainActivity
}