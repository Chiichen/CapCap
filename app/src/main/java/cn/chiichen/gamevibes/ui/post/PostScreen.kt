package cn.chiichen.gamevibes.ui.post

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cn.chiichen.gamevibes.R
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.OriginalSize
import coil.size.Scale
import coil.size.Size
import coil.transform.CircleCropTransformation

@Composable
fun PostScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.background(colorResource(id = R.color.grey))
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            IconButton(onClick = { /*TODO*/}) {
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
                Text(text = "帖子")
            }
            IconButton(onClick = {}) {}
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(
                topStart = CornerSize(60.dp),
                topEnd = CornerSize(60.dp),
                bottomEnd = CornerSize(0.dp),
                bottomStart = CornerSize(0.dp)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                ImageUpload()

                GameRelated()

                TextField(
                    value = "填写标题",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent, // 未聚焦时的底部指示线颜色
                        focusedIndicatorColor = Color.Transparent    // 聚焦时的底部指示线颜色
                    )
                )

                TextField(
                    value = "评价内容...",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        unfocusedIndicatorColor = Color.Transparent, // 未聚焦时的底部指示线颜色
                        focusedIndicatorColor = Color.Transparent    // 聚焦时的底部指示线颜色
                    )
                )

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(horizontal = 30.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "发布")
                }
            }
        }
    }
}


@Composable
fun ImageUpload(){
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var uriToDelete by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            selectedImageUris = selectedImageUris + uris
        }
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        items(selectedImageUris) { uri ->
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(data = uri)
                            .apply {
                                size(Size.ORIGINAL)
                            }.build()
                    ),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .clickable {
                            uriToDelete = uri
                        },
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
        item {
            IconButton(
                onClick = {
                    if (selectedImageUris.size < 9) {
                        launcher.launch("image/*")
                    } else {
                        Toast.makeText(context, "最多选择九张图片", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .background(Color.Gray)
                    .size(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    }

    if (uriToDelete != null) {
        AlertDialog(
            onDismissRequest = { uriToDelete = null },
            title = { Text("删除图像") },
            text = { Text("确定要删除这张图片吗？") },
            confirmButton = {
                Button(
                    onClick = {
                        selectedImageUris = selectedImageUris.filter { it != uriToDelete }
                        uriToDelete = null
                    }
                ) {
                    Text("删除")
                }
            },
            dismissButton = {
                Button(
                    onClick = { uriToDelete = null }
                ) {
                    Text("取消")
                }
            }
        )
    }
}

@Composable
fun GameRelated() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        val text = remember {"关联游戏"}
        Button(onClick = { /*TODO*/ }) {
            Text(text = text)
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun Prev (){
//    Lab()
    val navController = rememberNavController()
    PostScreen(navController = navController)
}