
package cn.chiichen.gamevibes.ui.messages

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.chiichen.gamevibes.ui.messages.comment.CommentScreen
import cn.chiichen.gamevibes.ui.messages.save.SaveScreen
import cn.chiichen.gamevibes.ui.messages.subscribe.SubscribeScreen
import cn.chiichen.gamevibes.ui.profile.like.LikeScreen

@Composable
fun MessagesScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    // Tabs
    TabRow(selectedTabIndex = selectedTabIndex) {
        Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }) {
            Text(text = "评论", modifier = Modifier.padding(16.dp))
        }
        Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }) {
            Text(text = "点赞", modifier = Modifier.padding(16.dp))
        }
        Tab(selected = selectedTabIndex == 2, onClick = { selectedTabIndex = 2 }) {
            Text(text = "收藏", modifier = Modifier.padding(16.dp))
        }
        Tab(selected = selectedTabIndex == 3, onClick = { selectedTabIndex = 3 }) {
            Text(text = "关注", modifier = Modifier.padding(16.dp))
        }
    }
    when (selectedTabIndex) {
        0 -> CommentScreen()
        1 -> LikeScreen()
        2 -> SaveScreen()
        3 -> SubscribeScreen()
    }
}

@Preview
@Composable
fun MessagesScreenPreview() {
    MessagesScreen()
}