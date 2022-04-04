package com.example.newsapptest.models

data class ResponseNews(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)