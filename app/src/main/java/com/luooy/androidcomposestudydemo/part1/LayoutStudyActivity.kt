package com.luooy.androidcomposestudydemo.part1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.luooy.androidcomposestudydemo.part1.ui.theme.AndroidComposeStudyDemoTheme

class LayoutStudyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeStudyDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

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
fun PhotographerCard(){
    Column {
        Text(text = "Alfred Sisley",fontWeight = FontWeight.Bold)
        
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidComposeStudyDemoTheme {
        PhotographerCard()
    }
}