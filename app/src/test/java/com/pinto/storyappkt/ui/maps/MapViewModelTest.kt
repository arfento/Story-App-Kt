package com.pinto.storyappkt.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.pinto.storyappkt.data.models.stories.StoriesResponse
import com.pinto.storyappkt.data.remote.StoryRepository
import com.pinto.storyappkt.data.remote.Result
import com.pinto.storyappkt.ui.map.MapsViewModel
import com.pinto.storyappkt.utils.DataDummy
import com.pinto.storyappkt.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var storyRepository: StoryRepository

    private lateinit var mapViewModel: MapsViewModel
    private val dummyStoriesResponse = DataDummy.generateDummyStories()

    @Before
    fun setUp() {
        mapViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when getStoriesWithLocation Should Not Null and return success`() {
        val expectedStoryResponse = MutableLiveData<Result<StoriesResponse>>()
        expectedStoryResponse.value = Result.Success(dummyStoriesResponse)

        `when`(storyRepository.getStoriesWithLocation()).thenReturn(expectedStoryResponse)

        val actualStories = mapViewModel.getStoriesWithLocation().getOrAwaitValue()
        Mockito.verify(storyRepository).getStoriesWithLocation()
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Result.Success)
        Assert.assertEquals(dummyStoriesResponse.listStory.size, (actualStories as Result.Success).data.listStory.size)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedStoryResponse = MutableLiveData<Result<StoriesResponse>>()
        expectedStoryResponse.value = Result.Error("network error")

        `when`(storyRepository.getStoriesWithLocation()).thenReturn(expectedStoryResponse)

        val actualStories = mapViewModel.getStoriesWithLocation().getOrAwaitValue()
        Mockito.verify(storyRepository).getStoriesWithLocation()
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Result.Error)
    }
}