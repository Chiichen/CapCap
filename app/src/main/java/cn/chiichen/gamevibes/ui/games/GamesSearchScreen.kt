package cn.chiichen.gamevibes.ui.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cn.chiichen.gamevibes.R
import cn.chiichen.gamevibes.model.entities.Game
import coil.compose.rememberAsyncImagePainter


@Composable
fun GamesSearchScreen(navController: NavController,viewModel: GamesViewModel){
    val relatedGames by viewModel.relatedGames.collectAsState()
    var searchText by remember { mutableStateOf(viewModel.searchText.value) }
    val listState = LazyListState()

    LaunchedEffect(Unit) {
        viewModel.getRelatedGames()
    }
    Column {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "",
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "游戏")
                }
                IconButton(onClick = {}) {}
            }
        },
            backgroundColor = colorResource(id = R.color.grey)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.grey)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        viewModel.updateSearchText(it)
                    },
                    modifier = Modifier.height(50.dp)
                )
                IconButton(
                    onClick = {
                        viewModel.updateSearchText(searchText)
                        viewModel.getRelatedGames()
                    }
                ){
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                }
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(relatedGames){item ->
                RowItem(navController,item)
            }
        }

        val shouldLoadMore = remember {
            derivedStateOf {
                val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                lastVisibleItemIndex != null && lastVisibleItemIndex >= relatedGames.size - 5
            }
        }

        LaunchedEffect(shouldLoadMore.value) {
            if (shouldLoadMore.value) {
                viewModel.getMoreRelatedGames()
            }
        }
    }
}

@Composable
private fun RowItem(navController: NavController,game: Game){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(80.dp)
            .clickable(onClick = {
                navController.navigate("gameDetail/${game.id}")
            }),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(painter = rememberAsyncImagePainter(game.image), contentDescription = "")
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = game.name)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "")
                Text(text = "${game.score}")
            }
        }
    }
}
