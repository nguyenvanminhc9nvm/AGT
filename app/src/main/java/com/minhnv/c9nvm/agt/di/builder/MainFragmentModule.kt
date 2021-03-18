package com.minhnv.c9nvm.agt.di.builder

import com.minhnv.c9nvm.agt.ui.comic.ComicFragment
import com.minhnv.c9nvm.agt.ui.comic.detail.ComicDetailFragment
import com.minhnv.c9nvm.agt.ui.comic.detail.description.ComicDescriptionFragment
import com.minhnv.c9nvm.agt.ui.humor.HumorFragment
import com.minhnv.c9nvm.agt.ui.main.AGTFragment
import com.minhnv.c9nvm.agt.ui.menu.MenuFragment
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

    @ContributesAndroidInjector
    abstract fun detailComicFragment(): ComicDetailFragment

    @ContributesAndroidInjector
    abstract fun agtFragment(): AGTFragment

    @ContributesAndroidInjector
    abstract fun descriptionComicFragment(): ComicDescriptionFragment

    @ContributesAndroidInjector
    abstract fun menuFragment(): MenuFragment

}