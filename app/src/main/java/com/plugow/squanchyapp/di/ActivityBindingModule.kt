package com.plugow.squanchyapp.di

import com.plugow.squanchyapp.di.scope.ActivityScoped
import com.plugow.squanchyapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity() : MainActivity
}