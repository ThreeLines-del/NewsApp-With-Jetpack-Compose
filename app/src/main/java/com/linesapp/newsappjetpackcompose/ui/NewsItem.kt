package com.linesapp.newsappjetpackcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linesapp.newsappjetpackcompose.models.Article

@Composable
fun NewsItem(
    article: Article,
    onItemClick: () -> Unit,
    onSaveOrDeleteIconClick: () -> Unit,
    saveOrDeleteIcon: ImageVector
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp)
            .clickable(
                onClick = onItemClick
            )
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "article image",
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
            )
            Icon(
                modifier = Modifier
                    .clickable(
                        onClick = onSaveOrDeleteIconClick
                    ),
                imageVector = saveOrDeleteIcon,
                contentDescription = "Save Icon"
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp)
        ) {
            article.title?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = it,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            article.description?.let {
                Text(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    text = it,
                    maxLines = Int.MAX_VALUE
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = article.source.name,
                fontWeight = FontWeight.Bold
            )
            article.publishedAt?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = it
                )
            }
        }
    }
}
