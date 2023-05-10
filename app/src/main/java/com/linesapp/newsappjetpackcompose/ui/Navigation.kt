package com.linesapp.newsappjetpackcompose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.linesapp.newsappjetpackcompose.ui.HomeScreen
import com.linesapp.newsappjetpackcompose.ui.NewsViewModel
import com.linesapp.newsappjetpackcompose.ui.screens.ArticleScreen
import com.linesapp.newsappjetpackcompose.ui.screens.SearchScreen

@Composable
fun Navigation(
    navController: NavHostController
){
    val viewModel = hiltViewModel<NewsViewModel>()

    NavHost(navController = navController, startDestination = "home_screen"){
        composable(route = "home_screen"){
            HomeScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(route = "save_screen"){
            SaveScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(route = "search_screen"){
            SearchScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = "article_screen/{website_url}",
            arguments = listOf(
                navArgument(name = "website_url"){
                    type = NavType.StringType
                }
            )
        ){ entry ->
            ArticleScreen(
                websiteUrl = entry.arguments?.getString("website_url"),
                viewModel = viewModel
            )
        }
    }
}