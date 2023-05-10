package com.linesapp.newsappjetpackcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.linesapp.newsappjetpackcompose.ui.NewsItem
import com.linesapp.newsappjetpackcompose.models.Article
import com.linesapp.newsappjetpackcompose.ui.NewsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SearchScreen(
    viewModel: NewsViewModel,
    navController: NavController
){
    var searchQuery by remember {
        mutableStateOf("")
    }

    var isLoading by remember(searchQuery) {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    
    var articles by remember(searchQuery) {
        mutableStateOf(emptyList<Article>())
            .apply {
                coroutineScope.launch {
                    delay(3000)
                    if(searchQuery.isNotBlank()){
                        isLoading = true
                        value = viewModel.searchNewsFlow(searchQuery).first()
                        isLoading = false
                    }
                }
            }
    }

    Box() {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    coroutineScope.launch {
                        delay(500)
                        if(it == searchQuery && searchQuery.isNotBlank()){
                            isLoading = true
                            articles = viewModel.searchNewsFlow(searchQuery).first()
                            isLoading = false
                        }
                    }
                },
                label = {
                    Text(text = "Search")
                }
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ){
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(16.dp)
                    )
                }

            }else{
                LazyColumn{
                    items(articles.size){ index ->
                        val searchArticle = articles[index]

                        NewsItem(
                            article = searchArticle,
                            onItemClick = {
                                val encodedUrl = URLEncoder.encode(searchArticle.url, StandardCharsets.UTF_8.toString())
                                navController.navigate("article_screen/$encodedUrl")
                            },
                            onSaveOrDeleteIconClick = {
                                viewModel.deleteArticle(searchArticle)
                            },
                            saveOrDeleteIcon = Icons.Default.Favorite
                        )
                    }
                }
            }
        }
    }
}
