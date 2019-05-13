package com.plugow.squanchyapp.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.plugow.squanchyapp.R
import com.plugow.squanchyapp.ui.fragment.CharactersFragment
import com.plugow.squanchyapp.ui.fragment.EpisodesFragment
import com.plugow.squanchyapp.ui.fragment.LocationsFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : DaggerAppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var fragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_episodes -> {
                fragment = EpisodesFragment.newInstance()
            }
            R.id.navigation_characters -> {
                fragment = CharactersFragment.newInstance()
            }
            R.id.navigation_locations -> {
                fragment = LocationsFragment.newInstance()
            }
        }
        loadFragment(fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_episodes
    }

    fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            return true
        }
        return false
    }
}
