package com.example.newsapptest.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapptest.models.Article

@Dao
interface NewsArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticleToDb(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticle(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}