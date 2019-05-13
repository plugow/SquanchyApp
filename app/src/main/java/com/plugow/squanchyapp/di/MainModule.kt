package com.plugow.squanchyapp.di

import com.plugow.squanchyapp.di.scope.FragmentScoped
import com.plugow.squanchyapp.ui.fragment.CharactersFragment
import com.plugow.squanchyapp.ui.fragment.EpisodesFragment
import com.plugow.squanchyapp.ui.fragment.LocationsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun episodesFragment() : EpisodesFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun charactersFragment() : CharactersFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun locationsFragment() : LocationsFragment
}