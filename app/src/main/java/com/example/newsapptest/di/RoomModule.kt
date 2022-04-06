package com.example.newsapptest.di

import android.content.Context
import androidx.room.Room
import com.example.newsapptest.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun getArticleDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context,ArticleDatabase::class.java, "article_db.db").build()

    @Provides
    @Singleton
    fun getNewsArticleDao(db: ArticleDatabase) = db.getNewsArticleDao()

}