package cn.chiichen.gamevibes.ui.games

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.chiichen.gamevibes.R

@Composable
fun GamesSearchScreen(navController: NavController){
    Column {
        TopAppBar(title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /*TODO*/ }) {
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
                TextField(value = "", onValueChange = {}, modifier = Modifier.height(30.dp))
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
private fun RowItem(item: Int){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

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
    GamesSearchScreen(navController = navController)
}