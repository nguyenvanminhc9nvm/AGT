package com.minhnv.c9nvm.agt.di.builder

import com.minhnv.c9nvm.agt.ui.comic.ComicFragment
import com.minhnv.c9nvm.agt.ui.humor.HumorFragment
import com.minhnv.c9nvm.agt.ui.sport.SportFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentModule {
    @ContributesAndroidInjector
    abstract fun comicFragment(): ComicFragment

    @ContributesAndroidInjector
    abstract fun humorFragment(): HumorFragment

    @ContributesAndroidInjector
    abstract fun sportFragment(): SportFragment
}