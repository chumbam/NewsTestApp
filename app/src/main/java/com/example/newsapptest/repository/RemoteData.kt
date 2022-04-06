package com.example.newsapptest.repository

import com.example.newsapptest.api.NewsApiService
import javax.inject.Inject

class RemoteData @Inject constructor(private val newsApiService: NewsApiService) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsApiService.getBreakingNews(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun searchForAllNews(searchQuery: String) =
        newsApiService.searchForAllNews(searchQuery = searchQuery)

}