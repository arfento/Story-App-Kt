package com.pinto.storyappkt.ui.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.pinto.storyappkt.data.models.register.RegisterResponse
import com.pinto.storyappkt.data.remote.Result
import com.pinto.storyappkt.data.remote.StoryRepository
import com.pinto.storyappkt.utils.DataDummy
import com.pinto.storyappkt.utils.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegistrViewModelTest {
    @get:Rule
    val instantExecuterRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegisterResponse = DataDummy.generateDummyRegister()

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(storyRepository)
    }

    @Test
    fun `when Register Should Not Null and return success`()= runTest {
        val expectedRegisterResponse = MutableLiveData<Result<RegisterResponse>>()
        expectedRegisterResponse.value = Result.Success(dummyRegisterResponse)
        val name = "name"
        val email = "name@gmail.com"
        val password = "password"

        Mockito.`when`(storyRepository.postRegister(name, email, password)).thenReturn(expectedRegisterResponse)

        val actualResponse = registerViewModel.register(name, email, password).getOrAwaitValue()
        Mockito.verify(storyRepository).postRegister(name, email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedRegisterResponse = MutableLiveData<Result<RegisterResponse>>()
        expectedRegisterResponse.value = Result.Error("network error")
        val name = "name"
        val email = "name@email.com"
        val password = "password"

        Mockito.`when`(storyRepository.postRegister(name, email, password))
            .thenReturn(expectedRegisterResponse)

        val actualResponse = registerViewModel.register(name, email, password).getOrAwaitValue()
        Mockito.verify(storyRepository).postRegister(name, email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
    }

}