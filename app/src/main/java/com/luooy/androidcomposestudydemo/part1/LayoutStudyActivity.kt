package com.luooy.androidcomposestudydemo.part1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.coil.rememberCoilPainter
import com.luooy.androidcomposestudydemo.R
import com.luooy.androidcomposestudydemo.part1.ui.theme.AndroidComposeStudyDemoTheme
import kotlinx.coroutines.launch

class LayoutStudyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeStudyDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    DefaultLayoutPreview()
                }
            }
        }
    }

    companion object {
        fun startLayoutStudyActivity(context: Context) {
            context.startActivity(Intent(context, LayoutStudyActivity::class.java))
        }
    }
}

@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(modifier
        .padding(start = 8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable { }
        .padding(16.dp)) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.cyberpunk2077_cdn_wallpaper),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("3 minutes ago", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun ScaffoldCompose() {
    val listSize = 100
    val scaffoldState = rememberScaffoldState()
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "LayoutsCodelab")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(0)
                    }
                }) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null)
                }

                Spacer(modifier = Modifier.height(16.dp))

                FloatingActionButton(onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }) {
                    Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null)
                }
            }
        },
        drawerContent = { DrawerContent { coroutineScope.launch { scaffoldState.drawerState.close() } } }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp),
            scrollState,
            listSize = listSize
        )
    }
}

@Composable
fun DrawerContent(onClick: () -> Unit) {
    Surface {
        Column {
            Row(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = painterResource(R.drawable.ic_baseline_android_24),
                    contentDescription = null, // decorative
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.app_name)
                )
            }

            Divider(color = MaterialTheme.colors.onSurface.copy(alpha = .2f))

            DrawerButton(
                icon = Icons.Filled.Home,
                label = "Home",
                action = onClick
            )

            DrawerButton(
                icon = Icons.Filled.ListAlt,
                label = "Interests",
                action = onClick
            )
        }
    }
}

@Composable
fun DrawerButton(
    icon: ImageVector,
    label: String,
    action: () -> Unit
) {
    val surfaceModifier = Modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = null, // decorative
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier, scrollState: LazyListState, listSize: Int) {
    Column(modifier = modifier) {
        PhotographerCard()
        SimpleList(scrollState, listSize)
    }
}

@Composable
fun SimpleList(scrollState: LazyListState, listSize: Int) {

    LazyColumn(state = scrollState, modifier = Modifier.fillMaxWidth()) {
        items(100) {
            ImageListItem(it)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun ImageListItem(index: Int) {
    Card {
        Column {
            Image(
                painter = rememberCoilPainter(
                    request = "https://source.unsplash.com/random",
                    fadeIn = true,
                    previewPlaceholder = R.drawable.ic_baseline_android_24
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp))
            )
            Spacer(Modifier.width(10.dp))
            Text("Item #$index", style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultLayoutPreview() {
    AndroidComposeStudyDemoTheme {
        ScaffoldCompose()
    }
}