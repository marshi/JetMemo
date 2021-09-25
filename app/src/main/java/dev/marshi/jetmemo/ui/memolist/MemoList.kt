package dev.marshi.jetmemo.ui.memolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.marshi.jetmemo.LocalNavController
import dev.marshi.jetmemo.NavDestination
import dev.marshi.jetmemo.ui.theme.JetMemoTheme

@Composable
fun MemoList() {
    LazyColumn {
        repeat(10) {
            item {
                MemoLine()
            }
        }
    }
}

@Composable
fun MemoLine() {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        val navController = LocalNavController.current
        Text("memo", Modifier.clickable {
            navController.navigate(NavDestination.MemoDetail.dest)
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetMemoTheme {
        MemoList()
    }
}