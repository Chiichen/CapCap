package cn.chiichen.gamevibes.ui.home.articleSearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cn.chiichen.gamevibes.R
import cn.chiichen.gamevibes.model.entities.Article
import cn.chiichen.gamevibes.utils.timeConvertor
import coil.compose.rememberAsyncImagePainter

@Composable
fun ArticleSearchResultScreen(navController: NavHostController,viewModel:ArticleSearchViewModel = viewModel()) {
    // 示例数据
    val searchResults : List<Article> = mutableListOf(
        Article(1,"title",100,
            "2024-06-02T14:15:22Z",10,
            "https://img0.baidu.com/it/u=350592823,3182430235&fm=253&fmt=auto&app=120&f=JPEG?w=1200&h=800",
            "测试类型"),
        Article(1,"title",100,
            "2024-06-02T14:15:22Z",10,
            "https://img0.baidu.com/it/u=350592823,3182430235&fm=253&fmt=auto&app=120&f=JPEG?w=1200&h=800",
            "测试类型"),
        Article(1,"title",100,
            "2024-06-02T14:15:22Z",10,
            "https://img0.baidu.com/it/u=350592823,3182430235&fm=253&fmt=auto&app=120&f=JPEG?w=1200&h=800",
            "测试类型")
    )
    var searchText by viewModel.searchText
    var order = 0
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {
        Row {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "返回首页"
                )
            }
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text(text = "搜索") },
                modifier = Modifier
                    .fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "搜索"
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = if (searchText.isNotEmpty()) ImeAction.Search else ImeAction.None
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.isNotEmpty()) {
                            //TODO
                        }
                    }
                ),
                singleLine = true
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ScrollableTabRow(
                modifier = Modifier.width(100.dp),
                backgroundColor = Color.White,
                selectedTabIndex = 0,
                edgePadding = 0.dp // 移除边缘填充使标签对齐到开始位置
            ) {
                Tab(
                    selected = true,
                    onClick = { },
                    text = { Text("全部") }
                )
            }

            IconButton(
                onClick = {
                    order = if(order == 0) 1 else 0
                    //todo
                },
                modifier = Modifier.padding(horizontal = 8.dp),
                content = {
                    Row {
                        Icon(Icons.Default.Menu, contentDescription = "Switch", tint = Color.Black) // 设置 tint 属性为图标着色
                        Text(if (order == 0) "测试1" else "测试2", color = Color.Black) // 设置文本颜色
                    }
                }
            )
        }
        LazyColumn {
            items(searchResults) { result ->
                RowItem(article = result)
            }
        }
    }
}

@Composable
private fun RowItem(article: Article) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(80.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
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
                    text = timeConvertor(article.postTime) + " • " + article.type,
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
            painter = rememberAsyncImagePainter(article.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(16 / 9f) // Aspect ratio of 16:9
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun pp(){
    val navController = rememberNavController()
    ArticleSearchResultScreen(navController = navController)
}

