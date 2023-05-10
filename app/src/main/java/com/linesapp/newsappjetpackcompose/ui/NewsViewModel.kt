package com.linesapp.newsappjetpackcompose.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linesapp.newsappjetpackcompose.models.Article
import com.linesapp.newsappjetpackcompose.models.NewsResponse
import com.linesapp.newsappjetpackcompose.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    val getSavedNews: Flow<List<Article>> = newsRepository.getSavedNews()

    fun breakingNewsFlow(countryCode: String): Flow<List<Article>> = flow {
        val breakingNews = newsRepository.getBreakingNews(countryCode)
        // map the breaking news response to a list of BreakingNewsItem objects
        val breakingNewsItems = mapResponseToBreakingNewsItems(breakingNews)
        emit(breakingNewsItems)
    }

    fun searchNewsFlow(searchQuery: String): Flow<List<Article>> = flow {
        val searchNews = newsRepository.searchNews(searchQuery)
        val searchNewsItems = mapResponseToBreakingNewsItems(searchNews)
        emit(searchNewsItems)
    }

    private fun mapResponseToBreakingNewsItems(response: Response<NewsResponse>): List<Article> {
        val breakingNewsItems = mutableListOf<Article>()
        for (article in response.body()!!.articles) {
            breakingNewsItems.add(
                Article(
                    title = article.title,
                    id = article.id,
                    author = article.author,
                    content = article.content,
                    description = article.description,
                    publishedAt = article.publishedAt,
                    source = article.source,
                    url = article.url,
                    urlToImage = article.urlToImage
                )
            )
        }
        return breakingNewsItems
    }

    fun upsertArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }
    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticles(article)
    }

}