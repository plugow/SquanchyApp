package com.plugow.squanchyapp

import com.plugow.squanchyapp.di.DaggerApiComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class SquanchyApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApiComponent.builder().application(this).build()

}