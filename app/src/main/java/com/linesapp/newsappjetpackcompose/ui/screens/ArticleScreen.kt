package com.linesapp.newsappjetpackcompose.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.linesapp.newsappjetpackcompose.ui.NewsViewModel

@Composable
fun ArticleScreen(
    websiteUrl : String?,
    viewModel: NewsViewModel
){

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        // Adding a WebView inside AndroidView
        // with layout as full screen
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                if (websiteUrl != null) {
                    loadUrl(websiteUrl)
                }
            }
        }, update = {
            if (websiteUrl != null) {
                it.loadUrl(websiteUrl)
            }
        })
    }
}

//@Preview
//@Composable
//fun ArticleScreenPreview(){
//    ArticleScreen()
//}