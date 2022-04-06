package com.example.newsapptest.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val remoteData: RemoteData,
    val localData: LocalData
) {

    suspend fun getBreakingNews(countryCode: String = "ru", pageNumber: Int) =
        remoteData.getBreakingNews(countryCode = countryCode, pageNumber = pageNumber)

}