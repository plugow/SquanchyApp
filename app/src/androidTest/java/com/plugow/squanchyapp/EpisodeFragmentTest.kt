package com.plugow.squanchyapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import androidx.test.runner.AndroidJUnit4
import com.plugow.squanchyapp.data.local.EpisodeResult
import com.plugow.squanchyapp.di.util.Event
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.ui.MainActivity
import com.plugow.squanchyapp.ui.fragment.EpisodesFragment
import com.plugow.squanchyapp.viewModel.EpisodeViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@MediumTest
@RunWith(AndroidJUnit4::class)
class EpisodeFragmentTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val activityRule = IntentsTestRule(MainActivity::class.java)

    @get:Rule
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @MockK
    lateinit var viewModel: EpisodeViewModel
    private lateinit var episodesFragment: EpisodesFragment

    var episodes = MutableLiveData<List<EpisodeResult>>()
    var isLoadingRefresh = MutableLiveData<Boolean>(true)
    var isLoading = MutableLiveData<Boolean>(false)
    var mainEvent = MutableLiveData<Event<MainEvent>>()

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        episodesFragment = EpisodesFragment.newInstance()
        episodesFragment.setViewModel(viewModel)
        activityRule.activity.loadFragment(episodesFragment)
        every {viewModel.episodes} returns episodes
        every {viewModel.isLoading } returns isLoading
        every {viewModel.isLoadingRefresh} returns (isLoadingRefresh)
        every {viewModel.mainEvent } returns mainEvent
        every { viewModel.onBottomReachedEpisodes() } returns Unit



    }

    @Test
    fun checkLoadingBinding() {
        isLoadingRefresh.value=false
        onView(withId(R.id.refreshEpisodes)).check(matches(withRefresh(false)))
    }


}