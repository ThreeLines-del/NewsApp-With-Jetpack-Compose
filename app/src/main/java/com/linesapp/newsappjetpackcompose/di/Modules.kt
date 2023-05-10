package com.linesapp.newsappjetpackcompose.di

import android.app.Application
import com.linesapp.newsappjetpackcompose.api.NewsAPI
import com.linesapp.newsappjetpackcompose.api.RetrofitInstance
import com.linesapp.newsappjetpackcompose.db.ArticleDao
import com.linesapp.newsappjetpackcompose.db.ArticleDatabase
import com.linesapp.newsappjetpackcompose.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Provides
    @Singleton
    fun providesArticleDatabase(application: Application): ArticleDatabase{
        return ArticleDatabase.getInstance(application)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(db: ArticleDatabase): NewsRepository{
        return NewsRepository(db)
    }

    @Provides
    @Singleton
    fun providesArticleDao(articleDatabase: ArticleDatabase): ArticleDao{
        return articleDatabase.articleDao()
    }

    @Provides
    @Singleton
    fun providesNewsAPI(): NewsAPI{
        return RetrofitInstance.api
    }
}