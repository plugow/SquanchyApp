package com.plugow.squanchyapp.ui.fragment

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.plugow.squanchyapp.R
import com.plugow.squanchyapp.databinding.FragmentEpisodesBinding
import com.plugow.squanchyapp.ui.adapter.EpisodeAdapter
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.viewModel.EpisodeViewModel
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.design.snackbar
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class EpisodesFragment : DaggerFragment() {
    @VisibleForTesting
    @Inject lateinit var viewModelFactory:ViewModelProvider.Factory
    private lateinit var mViewModel: EpisodeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory).get(EpisodeViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val mAdapter= EpisodeAdapter()
        val binding = DataBindingUtil.inflate<FragmentEpisodesBinding>(inflater, R.layout.fragment_episodes, container, false ).apply {
            lifecycleOwner = this@EpisodesFragment
            viewModel = mViewModel
            list.adapter = mAdapter
            list.layoutManager = LinearLayoutManager(context)
        }

        val layout = binding.episodesLayout
        mViewModel.getEpisodes()
        mViewModel.mainEvent.observe(viewLifecycleOwner, Observer {
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
                EpisodesFragment()
    }

    @TestOnly
    fun setViewModel(mockViewModel: EpisodeViewModel){
        mViewModel = mockViewModel
    }

}
