package com.luooy.androidcomposestudydemo

import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.luooy.androidcomposestudydemo.part2.BasicStateActivity
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

@Preview(showSystemUi = true)
@Composable
fun MainView() {
    val context = LocalContext.current
    val mainData = mutableListOf(
        Triple(true, "Part 1", {}),
        Triple(false, "Basis study", {
            BasisStudyActivity.startBasisStudyActivity(context)
        }),
        Triple(
            false,
            "Layout study",
            { LayoutStudyActivity.startLayoutStudyActivity(context) }),
        Triple(
            false,
            "Custom layout study",
            { CustomLayoutStudyActivity.startCustomLayoutStudyActivity(context) }),
        Triple(true, "Part 2", {}),
        Triple(
            false,
            "Basis state study",
            { BasicStateActivity.startBasicStateActivity(context) })
    )

    LazyColumn {
        items(mainData) {
            if (it.first) {
                Text(
                    text = it.second,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                TextButton(
                    onClick = it.third,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(start = 16.dp)
                ) {
                    Text(
                        text = it.second,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}