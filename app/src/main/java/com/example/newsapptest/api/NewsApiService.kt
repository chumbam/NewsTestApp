package com.example.newsapptest.api

import com.example.newsapptest.models.Article
import com.example.newsapptest.models.ResponseNews
import com.example.newsapptest.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "ru",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<ResponseNews>

    @GET("/v2/everything")
    suspend fun searchForAllNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<ResponseNews>
}