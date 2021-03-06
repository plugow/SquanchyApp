package com.plugow.squanchyapp.ui.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.plugow.squanchyapp.R
import com.plugow.squanchyapp.databinding.FragmentLocationsBinding
import com.plugow.squanchyapp.ui.adapter.LocationAdapter
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.viewModel.LocationViewModel
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject


class LocationsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(LocationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mAdapter = LocationAdapter()
        val binding = DataBindingUtil.inflate<FragmentLocationsBinding>(inflater, R.layout.fragment_locations, container, false ).apply {
            lifecycleOwner = this@LocationsFragment
            viewModel = mViewModel
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(context)
        }
        val layout = binding.locationsLayout
        mViewModel.getItems()
        mViewModel.event.observe(this, Observer {
            when(it.getContentIfNotHandled()){
                MainEvent.ERROR -> layout.snackbar(getString(R.string.error_message))
                MainEvent.NO_MORE -> layout.snackbar(getString(R.string.no_more))
            }
        })
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
                LocationsFragment()
    }

}
