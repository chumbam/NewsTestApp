package com.example.newsapptest.repository

import com.example.newsapptest.database.NewsArticleDAO
import com.example.newsapptest.models.Article
import javax.inject.Inject

class LocalData @Inject constructor(private val newsArticleDAO: NewsArticleDAO) {

    suspend fun addArticleToDb(article: Article) = newsArticleDAO.addArticleToDb(article)
    fun getAllArticle() = newsArticleDAO.getAllArticle()
    suspend fun deleteArticle (article: Article) = newsArticleDAO.deleteArticle(article)

}