package com.example.newsapptest.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapptest.models.Article

interface NewsArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticleToDb(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticle(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}