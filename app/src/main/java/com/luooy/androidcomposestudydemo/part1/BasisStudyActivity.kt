package com.luooy.androidcomposestudydemo.part1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luooy.androidcomposestudydemo.R
import com.luooy.androidcomposestudydemo.part1.ui.theme.AndroidComposeStudyDemoTheme
import com.luooy.androidcomposestudydemo.part1.ui.theme.Purple200
import com.luooy.androidcomposestudydemo.part1.ui.theme.Purple500

class BasisStudyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeStudyDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PreviewContentView()
                }
            }
        }
    }

    companion object {
        fun startBasisStudyActivity(context: Context) {
            context.startActivity(Intent(context, BasisStudyActivity::class.java))
        }
    }
}

@Composable
fun NewStory() {
    MaterialTheme {
        Column(
            Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = R.mipmap.cyberpunk2077_cdn_wallpaper),
                contentDescription = null,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "A day wandering through the sandhills " +
                        "in Shark Fin Cove, and a few of the " +
                        "sights I saw",
                style = typography.h6,
                color = colors.error
            )
            Text("Davenport, California", style = typography.body2.copy(color = Color.Yellow))
            Text("December 2018", style = typography.body2)
        }
    }
}

@Composable
fun ListView(myList: List<String>, onClick: (String) -> Unit) {
    /*val isSelectedList = remember { mutableStateOf(MutableList(myList.size) { false }) }
    val backgroundColorList = remember {
        mutableStateOf(List(myList.size) {
            if (isSelectedList.value[it]) {
                Color.Red
            } else {
                Color.Transparent
            }
        })
    }*/
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple200)
            .padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            Text(
                "this is header",
                modifier = Modifier
                    .background(Purple500)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
        /*itemsIndexed(myList) { index, data ->
            Text(
                "Item: $data",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = animateColorAsState(backgroundColorList.value[index]).value)
                    .clickable {
                        isSelectedList.value[index] = !isSelectedList.value[index]
                        onClick(data)
                    }
            )
        }*/
        items(myList) {
            val isSelected = remember { mutableStateOf(false) }
            val backgroundColor =
                animateColorAsState(targetValue = if (isSelected.value) Color.Red else Color.Transparent)
            Text(
                "Item: $it",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = backgroundColor.value)
                    .clickable {
                        isSelected.value = !isSelected.value
                        onClick(it)
                    }
            )
            Divider(color = Color.Gray)
        }
        item {
            Text(
                "this is footer", modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple500),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CounterButton() {
    val count = remember { mutableStateOf(0) }

    Button(onClick = { count.value++ }) {
        Text(
            text = "clicked ${count.value} times",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ContainSpacerView(content: @Composable () -> Unit) {
    content()
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun EditView() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        val name = rememberSaveable { mutableStateOf("") }
        if (name.value.isNotEmpty()) {
            Text(
                text = "Hello,${name.value}!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = typography.h5
            )
        }
        OutlinedTextField(
            value = name.value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { name.value = it },
            label = { Text(text = "Name") })
    }
}

@Composable
fun ExpandingCard(title: String, body: String) {
    val expanded = remember { mutableStateOf(false) }

    // describe the card for the current state of expanded
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Card {
            Column(
                Modifier
                    .width(280.dp)
                    .animateContentSize() // automatically animate size when it changes
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                Text(text = title)

                // content of the card depends on the current value of expanded
                if (expanded.value) {
                    Text(text = body, Modifier.padding(top = 8.dp))
                    // change expanded in response to click events
                    IconButton(
                        onClick = { expanded.value = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_expand_less_24),
                            contentDescription = null
                        )
                    }
                } else {
                    // change expanded in response to click events
                    IconButton(
                        onClick = { expanded.value = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_expand_more_24),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewContentView() {
    Column() {
        NewStory()

        ContainSpacerView {
            CounterButton()
        }

        ContainSpacerView {
            EditView()
        }

        ContainSpacerView {
            ExpandingCard(
                "title",
                "this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body,this is body"
            )
        }

        val context = LocalContext.current
        val list = (0..50).map { "This is count:$it" }.toList()
        ListView(list) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}
