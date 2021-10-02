package dev.marshi.jetmemo.ui.memodetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.NavControllerWrapperForPreview
import dev.marshi.jetmemo.ui.theme.JetMemoTheme

@Composable
fun MemoDetailScreen(navControllerWrapper: NavControllerWrapper) {
    Surface {
        MemoDetail(navControllerWrapper)
    }
}

@Composable
fun MemoDetail(navControllerWrapper: NavControllerWrapper) {
    val memoTextState = remember { mutableStateOf(TextFieldValue()) }
    Column {
        TopAppBar(
            title = { Text("title") },
            navigationIcon = {
                IconButton(onClick = {
                    navControllerWrapper.popBackStack()
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            actions = {
                TextButton(onClick = {}) {
                    Text("保存", color = Color.Black)
                }
            },
        )
        TextField(
            value = memoTextState.value,
            onValueChange = { memoTextState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Text("detail")
    }
}

@Preview
@Composable
fun MemoDetailScreenPreview() {
    JetMemoTheme {
        MemoDetailScreen(NavControllerWrapperForPreview())
    }
}
