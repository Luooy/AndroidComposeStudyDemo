package com.luooy.androidcomposestudydemo.part1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.luooy.androidcomposestudydemo.R
import com.luooy.androidcomposestudydemo.ui.theme.AndroidComposeStudyDemoTheme
import kotlin.math.max

class CustomLayoutStudyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidComposeStudyDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CustomLayoutPreview()
                }
            }
        }
    }

    companion object {
        fun startCustomLayoutStudyActivity(context: Context) {
            context.startActivity(Intent(context, CustomLayoutStudyActivity::class.java))
        }
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun SingleCustom(name: String) {
    Row {
        Text(text = "Hello $name!", Modifier.firstBaselineToTop(16.dp))
        Text(text = "Hello $name!", Modifier.padding(top = 16.dp))
    }

}

@Composable
fun CustomLayoutText() {
    CustomLayout {
        Text(text = "use CustomLayout 1")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "use CustomLayout 2")
    }
}

@Composable
fun ChipView(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun CustomLayout(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        var rowWidth = 0
        var rowHeight = 0

        val placeables = measurables.map {

            val placeable = it.measure(constraints)

            rowWidth += placeable.width
            rowHeight += placeable.height
            placeable
        }

        val width = rowWidth.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))

        val height = rowHeight
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        var yPosition = 0
        // Set the size of the layout as big as it can
        layout(width, height) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->

            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumBy { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        // Set the size of the parent layout
        layout(width, height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (avatar, name, desc, star) = createRefs()
        Image(
            painter = painterResource(id = R.mipmap.cyberpunk2077_cdn_wallpaper),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape)
                .constrainAs(avatar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = 16.dp)
                })

        Text(text = "V", Modifier.constrainAs(name) {
            top.linkTo(parent.top)
            start.linkTo(avatar.end, margin = 16.dp)
        })

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = "我就是大名鼎鼎的 V ，快来关注我吧！",
                fontSize = 12.sp, modifier = Modifier.constrainAs(desc) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(avatar.end, margin = 16.dp)
                })
        }

        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(star) {
            end.linkTo(parent.end, margin = 16.dp)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(desc.end, margin = 16.dp)
        }) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = null)
            Text(text = "star")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomLayoutPreview() {
    AndroidComposeStudyDemoTheme {
        Column {
            ContainSpacerView {
                SingleCustom("Android")
            }

            ContainSpacerView {
                CustomLayoutText()
            }

            Row(
                Modifier.horizontalScroll(rememberScrollState())
            ) {
                StaggeredGrid {
                    for (topic in topics) {
                        ChipView(modifier = Modifier.padding(8.dp), text = topic)
                    }
                }
            }

            ConstraintLayoutContent()
        }
    }
}