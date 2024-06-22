package cn.chiichen.gamevibes.ui.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cn.chiichen.gamevibes.R

@Composable
fun GamesScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopAppBar(title = { Text(text = "游戏") },
            backgroundColor = colorResource(id = R.color.grey))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScrollableTabRow(
                modifier = Modifier.width(100.dp),
                backgroundColor = colorResource(id = R.color.white),
                selectedTabIndex = 0,
                edgePadding = 0.dp, // Remove edge padding to align tabs to the start
                tabs = {
                        Tab(
                            selected = true,
                            onClick = { },
                            text = { Text("榜单") }
                        )
                },

            )
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .height(30.dp)
                    .weight(1f)
            )
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            }
        }
        LazyColumn {
            items(10) {item ->
                RowItem(item)
            }
        }
    }
}

@Composable
private fun RowItem(item: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "$item" , modifier = Modifier.width(20.dp))
        Image(painter = painterResource(id = R.drawable.image1), contentDescription = "")
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = "title$item")
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "")
                Text(text = "8.7")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Prev (){
    val navController = rememberNavController()
    GamesScreen(navController = navController)
}
