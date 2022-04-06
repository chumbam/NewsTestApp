package com.example.newsapptest.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapptest.models.Article
import com.example.newsapptest.models.ResponseNews
import com.example.newsapptest.repository.NewsRepository
import com.example.newsapptest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    val brNews: MutableLiveData<Resource<ResponseNews>> = MutableLiveData()
    var brNewsPage = 1

    val searchNews: MutableLiveData<Resource<ResponseNews>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews(countryCode = "ru")
    }

    //Remote Request
    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        brNews.postValue(Resource.Loading())
        val response =
            repository.getBreakingNews(countryCode = countryCode, pageNumber = brNewsPage)
        brNews.postValue(brNewsResponseHandle(response))
    }

    //Remote Request
    fun searchForAllNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = repository.searchForAllNews(searchQuery = searchQuery)
        searchNews.postValue(searchNewsResponseHandle(response))
    }

    //Remote Request
    private fun brNewsResponseHandle(response: Response<ResponseNews>): Resource<ResponseNews> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    //Remote Request
    private fun searchNewsResponseHandle(response: Response<ResponseNews>): Resource<ResponseNews> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    //Local Request
    fun saveArticleInDb(article: Article) = viewModelScope.launch {
        repository.addArticleToDb(article)
    }

    //Local Request
    fun getSavedArticle() = repository.getAllArticles()

    //Local Request
    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }
}