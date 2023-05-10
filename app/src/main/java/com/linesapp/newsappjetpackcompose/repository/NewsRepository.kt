package com.linesapp.newsappjetpackcompose.repository

import com.linesapp.newsappjetpackcompose.api.RetrofitInstance
import com.linesapp.newsappjetpackcompose.db.ArticleDatabase
import com.linesapp.newsappjetpackcompose.models.Article
import kotlinx.coroutines.flow.Flow

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String) =
        RetrofitInstance.api.getBreakingNews(countryCode)

    suspend fun searchNews(searchQuery: String) =
        RetrofitInstance.api.searchForNews(searchQuery)

    suspend fun upsert(article: Article) = db.articleDao().upsert(article)

    fun getSavedNews(): Flow<List<Article>> = db.articleDao().getAllArticles()

    suspend fun deleteArticles(article: Article) = db.articleDao().deleteArticle(article)
}