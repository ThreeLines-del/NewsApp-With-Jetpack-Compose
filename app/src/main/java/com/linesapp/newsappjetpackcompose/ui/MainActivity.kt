package com.linesapp.newsappjetpackcompose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.linesapp.newsappjetpackcompose.BottomNavigationBar
import com.linesapp.newsappjetpackcompose.Navigation
import com.linesapp.newsappjetpackcompose.ui.theme.NewsAppJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route = "home_screen",
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = "Saved",
                                        route = "save_screen",
                                        icon = Icons.Default.Favorite
                                    ),
                                    BottomNavItem(
                                        name = "Search",
                                        route = "search_screen",
                                        icon = Icons.Default.Search
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                }
                            )
                        }
                    ) {
                        Navigation(navController)
                    }

                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    navController: NavController
){
    val countryCode = "us"
    val breakingNewsAsState by viewModel.breakingNewsFlow(countryCode).collectAsState(initial = listOf())
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn{
            items(breakingNewsAsState.size){index ->
                val article = breakingNewsAsState[index]
                NewsItem(
                    article = article,
                    onItemClick = {
                        val encodedUrl = URLEncoder.encode(article.url, StandardCharsets.UTF_8.toString())
                        navController.navigate("article_screen/$encodedUrl")
                    },
                    onSaveOrDeleteIconClick = {
                        viewModel.upsertArticle(article)
                        navController.navigate("save_screen")
                    },
                    saveOrDeleteIcon = Icons.Default.Favorite
                )
            }
        }
    }
}