package com.example.newsapptest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptest.models.ResponseNews
import com.example.newsapptest.repository.NewsRepository
import com.example.newsapptest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    val brNews: MutableLiveData<Resource<ResponseNews>> = MutableLiveData()
    var brNewsPage = 1

    val searchNews: MutableLiveData<Resource<ResponseNews>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews(contreCode = "ru")
    }

    private fun getBreakingNews(contreCode: String) = viewModelScope.launch {
        brNews.postValue(Resource.Loading())
        val response = repository.getBreakingNews(countryCode = contreCode,pageNumber = brNewsPage)
        brNews.postValue(brNewsResponseHandle(response))
    }

    fun searchForAllNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.searchForAllNews(searchQuery = searchQuery)
        searchNews.postValue(searchNewsResponseHandle(response))
    }

    private fun brNewsResponseHandle(response: Response<ResponseNews>): Resource<ResponseNews> {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun searchNewsResponseHandle(response: Response<ResponseNews>): Resource<ResponseNews> {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}