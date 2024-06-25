package cn.chiichen.gamevibes.ui.home.hot

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cn.chiichen.gamevibes.model.entities.Article
import coil.compose.rememberAsyncImagePainter

@Composable
fun Hot(navController: NavController,viewModel: HotViewModel = viewModel()) {
    val articles by viewModel.articles.collectAsState()
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        itemsIndexed(articles) {index, article ->
            RowItem(navController,index = index, article = article)
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            lastVisibleItemIndex != null && lastVisibleItemIndex >= articles.size - 5
        }
    }

    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            viewModel.loadMore()
        }
    }
}

@Composable
private fun RowItem(navController: NavController,index: Int, article: Article) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(80.dp)
            .clickable(onClick = {
                navController.navigate("article/${article.id}")
            }),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.width(40.dp),
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${index + 1}",
                fontSize = 20.sp
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .height(80.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = article.title,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.padding(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${article.pv}阅读" + " • " + article.type,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "${article.comments}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(5.dp))
        Image(
            painter = rememberAsyncImagePainter(article.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(16 / 9f) // Aspect ratio of 16:9
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}
