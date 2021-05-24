package com.luooy.androidcomposestudydemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luooy.androidcomposestudydemo.part1.BasisStudyActivity
import com.luooy.androidcomposestudydemo.part1.CustomLayoutStudyActivity
import com.luooy.androidcomposestudydemo.part1.LayoutStudyActivity
import com.luooy.androidcomposestudydemo.ui.theme.AndroidComposeStudyDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeStudyDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
                        MainView()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainView() {
    val context = LocalContext.current
    LazyColumn {
        item {
            Text(
                text = "Part 1",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            TextButton(
                onClick = { BasisStudyActivity.startBasisStudyActivity(context) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 16.dp)
            ) {
                Text(
                    text = "Basis study",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        }
        item {
            TextButton(
                onClick = { LayoutStudyActivity.startLayoutStudyActivity(context) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 16.dp)
            ) {
                Text(
                    text = "Layout study", modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        }
        item {
            TextButton(
                onClick = { CustomLayoutStudyActivity.startCustomLayoutStudyActivity(context) },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 16.dp)
            ) {
                Text(
                    text = "Custom layout study", modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}