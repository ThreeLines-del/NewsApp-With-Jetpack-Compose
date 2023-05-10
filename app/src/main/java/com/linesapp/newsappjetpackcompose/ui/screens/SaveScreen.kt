package com.linesapp.newsappjetpackcompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.linesapp.newsappjetpackcompose.ui.NewsItem
import com.linesapp.newsappjetpackcompose.ui.NewsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SaveScreen(
    viewModel: NewsViewModel,
    navController: NavController
){
    val savedArticleState by viewModel.getSavedNews.collectAsState(initial = listOf())
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        LazyColumn{
            items(savedArticleState.size){ index ->
                val savedArticle = savedArticleState[index]

                NewsItem(
                    article = savedArticle,
                    onItemClick = {
                        val encodedUrl = URLEncoder.encode(savedArticle.url, StandardCharsets.UTF_8.toString())
                        navController.navigate("article_screen/$encodedUrl")
                    },
                    onSaveOrDeleteIconClick = {
                        viewModel.deleteArticle(savedArticle)
                    },
                    saveOrDeleteIcon = Icons.Default.Delete
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun SaveScreenPreview(){
//    SaveScreen()
//}