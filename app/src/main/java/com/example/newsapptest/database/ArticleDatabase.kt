package com.example.newsapptest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapptest.models.Article
import com.example.newsapptest.utils.Converter

@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class ArticleDatabase: RoomDatabase() {

    abstract fun getNewsArticleDao(): NewsArticleDAO


}