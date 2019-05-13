package com.plugow.squanchyapp.di

import android.app.Application
import com.plugow.squanchyapp.SquanchyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [ApiModule::class, AndroidSupportInjectionModule::class, AppModule::class,
    ActivityBindingModule::class, ViewModelModule::class])
interface ApiComponent : AndroidInjector<SquanchyApp> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApiComponent
    }
}