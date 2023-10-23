package com.pinto.storyappkt.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.pinto.storyappkt.data.models.login.LoginResponse
import com.pinto.storyappkt.data.models.register.RegisterResponse
import com.pinto.storyappkt.data.models.stories.PostStoryResponse
import com.pinto.storyappkt.data.models.stories.StoriesResponse
import com.pinto.storyappkt.data.models.stories.Story
import com.pinto.storyappkt.data.remote.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryRepository (private val apiService: ApiService){
    fun getStories(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }

    fun getStoriesWithLocation(): LiveData<Result<StoriesResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getStoriesWithLocation(1)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("ListStoryViewModel", "getStoriesWithLocation: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }



    fun postLogin(email : String, password : String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postLogin(email, password)
            emit(Result.Success(response))
        }catch (exception : Exception){
            Log.e("LoginViewModel", "postLogin: ${exception.message.toString()}")
            emit(Result.Error(exception.message.toString()))
        }
    }

    fun postRegister(name: String, email : String, password : String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        }catch (exception : Exception){
            Log.e("SignUpViewModel", "postSignUp: ${exception.message.toString()}")
            emit(Result.Error(exception.message.toString()))
        }
    }

    fun postStory(file: MultipartBody.Part, description: RequestBody): LiveData<Result<PostStoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.postStory(file, description)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("CreateStoryViewModel", "postStory: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
}