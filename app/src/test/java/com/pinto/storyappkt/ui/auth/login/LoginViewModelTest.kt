package com.pinto.storyappkt.ui.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.pinto.storyappkt.data.models.login.LoginResponse
import com.pinto.storyappkt.data.remote.Result
import com.pinto.storyappkt.data.remote.StoryRepository
import com.pinto.storyappkt.utils.DataDummy
import com.pinto.storyappkt.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val instantExecuterRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    private lateinit var loginViewModel: LoginViewModel
    private val dummyLoginResponse = DataDummy.generateDummyLogin()

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(storyRepository)
    }

    @Test
    fun `when login Should Not Null and return success`() {
        val expectedLoginResponse = MutableLiveData<Result<LoginResponse>>()
        expectedLoginResponse.value = Result.Success(dummyLoginResponse)
        val email = "name@gmail.com"
        val password = "password"

        Mockito.`when`(storyRepository.postLogin(email, password)).thenReturn(expectedLoginResponse)

        val actualResponse = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(storyRepository).postLogin(email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Success)
    }

    @Test
    fun `when Network Error Should Return Error`(){
        val expectedLoginResponse = MutableLiveData<Result<LoginResponse>>()
        expectedLoginResponse.value = Result.Error("network error")
        val email = "name@email.com"
        val password = "password"

        Mockito.`when`(storyRepository.postLogin(email, password)).thenReturn(expectedLoginResponse)

        val actualResponse = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(storyRepository).postLogin(email, password)
        Assert.assertNotNull(actualResponse)
        Assert.assertTrue(actualResponse is Result.Error)
    }
}