package dev.marshi.jetmemo.ui.memolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.marshi.jetmemo.NavDestination
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.NavControllerWrapperForPreview
import dev.marshi.jetmemo.ui.theme.JetMemoTheme


@Composable
fun MemoListScreen(navController: NavControllerWrapper) {
    MemoList(navController)
}

@Composable
fun MemoList(navController: NavControllerWrapper) {
    LazyColumn {
        repeat(10) {
            item {
                MemoLine(modifier = Modifier.clickable {
                    navController.navigate(
                        NavDestination.MemoDetail.dest
                    )
                })
            }
        }
    }
}

@Composable
fun MemoLine(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        Text("memo")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetMemoTheme {
        MemoListScreen(NavControllerWrapperForPreview())
    }
}