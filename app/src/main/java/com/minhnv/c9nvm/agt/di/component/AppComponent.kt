package com.minhnv.c9nvm.agt.di.component

import android.app.Application
import com.minhnv.c9nvm.agt.AGTApplication
import com.minhnv.c9nvm.agt.di.builder.ActivityBuilder
import com.minhnv.c9nvm.agt.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilder::class, AppModule::class])
interface AppComponent: AndroidInjector<AGTApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


}