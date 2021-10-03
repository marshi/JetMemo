package dev.marshi.jetmemo.ui.memodetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.NavControllerWrapperForPreview
import dev.marshi.jetmemo.ui.theme.JetMemoTheme

@Composable
fun MemoDetailScreen(
    navControllerWrapper: NavControllerWrapper,
    viewModel: MemoDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Surface {
        MemoDetail(
            navControllerWrapper,
            state,
            onSave = {
                viewModel.saveNewMemo(it)
            }
        )
    }
}

@Composable
fun MemoDetail(
    navControllerWrapper: NavControllerWrapper,
    state: MemoDetailScreenState,
    onSave: (text: String) -> Unit
) {
    val memoTextState = remember { mutableStateOf(TextFieldValue(text = state.text)) }
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
                TextButton(onClick = { onSave(memoTextState.value.text) }) {
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
