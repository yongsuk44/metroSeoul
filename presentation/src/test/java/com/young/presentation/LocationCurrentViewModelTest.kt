package com.young.presentation

import android.location.Location
import androidx.lifecycle.asLiveData
import com.google.android.gms.tasks.Tasks
import com.young.presentation.viewmodel.LocationCurrentViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@FlowPreview
@ExperimentalCoroutinesApi
class LocationCurrentViewModelTest {

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()


    lateinit var viewModel: LocationCurrentViewModel

    @Before
    fun setUp() {
        viewModel = LocationCurrentViewModel(mockk())
    }

    @Test
    fun `get GoogleService LastLocation Test`() = runBlockingTest {
        val location = mockk<Location>()
        every { location.longitude } returns 37.0
        every { location.latitude } returns 123.0

        val task = Tasks.forResult(location)


        val value = viewModel.getGoogleServiceLastLocation(task).asLiveData()
        value
    }
}
