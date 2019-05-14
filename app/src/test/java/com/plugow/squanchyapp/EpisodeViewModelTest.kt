package com.plugow.squanchyapp

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.plugow.squanchyapp.data.local.EpisodeResult
import com.plugow.squanchyapp.data.local.Episodes
import com.plugow.squanchyapp.data.local.Info
import com.plugow.squanchyapp.data.remote.ApiService
import com.plugow.squanchyapp.di.util.Event
import com.plugow.squanchyapp.di.util.MainEvent
import com.plugow.squanchyapp.viewModel.EpisodeViewModel
import io.reactivex.Scheduler
import io.reactivex.internal.schedulers.ExecutorScheduler
import junit.framework.Assert.assertEquals
import org.junit.rules.TestRule
import org.junit.Rule
import java.util.concurrent.Executor


class EpisodeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val immediateScheduler = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }
    }

    @Mock
    lateinit var mockService: ApiService

    @Mock lateinit var observer:Observer<Event<MainEvent>>
    lateinit var viewModel: EpisodeViewModel

    @Before
    fun setUp(){
        RxJavaPlugins.setInitIoSchedulerHandler { immediateScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediateScheduler }
        MockitoAnnotations.initMocks(this)
        viewModel = EpisodeViewModel(mockService)
    }


    @Test
    fun `show error when getEpisodes() returning error`() {
        val error = "Test error"
        val single: Single<Episodes> = Single.create {
            emitter ->
            emitter.onError(Exception(error))
        }

        whenever(mockService.getEpisodes(anyInt())).thenReturn(single)
        viewModel.mainEvent.observeForever(observer)
        viewModel.getEpisodes()
        assertEquals(viewModel.mainEvent.value?.peekContent(),MainEvent.ERROR)

    }

    @Test
    fun `validate episodes data`(){
        val episodes = Episodes(Info(1,1,"test", "test"), listOf(EpisodeResult(1, "name")))
        val single: Single<Episodes> = Single.create {
            emitter ->
            emitter.onSuccess(episodes)
        }
        whenever(mockService.getEpisodes(anyInt())).thenReturn(single)
        viewModel.getEpisodes()
        assertEquals(episodes.results,viewModel.episodes.value)
    }


    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }


}