package com.example.newsapptest.repository

import com.example.newsapptest.models.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val remoteData: RemoteData,
    val localData: LocalData
) {

    //Remote request
    suspend fun getBreakingNews(countryCode: String = "ru", pageNumber: Int) =
        remoteData.getBreakingNews(countryCode = countryCode, pageNumber = pageNumber)

    suspend fun searchForAllNews(searchQuery: String) =
        remoteData.searchForAllNews(searchQuery = searchQuery)

    //Local Request
    suspend fun addArticleToDb(article: Article) = localData.addArticleToDb(article = article)
    fun getAllArticles() = localData.getAllArticle()
    suspend fun deleteArticle(article: Article) = localData.deleteArticle(article = article)

}