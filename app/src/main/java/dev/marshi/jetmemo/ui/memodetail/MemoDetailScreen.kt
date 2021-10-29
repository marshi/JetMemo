package dev.marshi.jetmemo.ui.memodetail

import android.support.v4.media.MediaBrowserCompat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.NavControllerWrapperForPreview
import dev.marshi.jetmemo.ui.player.Player
import dev.marshi.jetmemo.ui.player.PlayerScreen
import dev.marshi.jetmemo.ui.player.PlayerState
import dev.marshi.jetmemo.utils.extractActivity

@Composable
fun MemoDetailScreen(
    navControllerWrapper: NavControllerWrapper,
    viewModel: MemoDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context.extractActivity()
    lateinit var mediaBrowser: MediaBrowserCompat

    Surface {
        MemoDetail(
            navControllerWrapper,
            recordButtons = {
                RecordButtons(
                    onStart = { viewModel.startRecording("file") },
                    onStop = { viewModel.stopRecording() }
                )
            },
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
    recordButtons: @Composable () -> Unit,
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
        recordButtons()
        TextField(
            value = memoTextState.value,
            onValueChange = { memoTextState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        PlayerScreen()
    }
}

@Composable
fun RecordButtons(onStart: () -> Unit = {}, onStop: () -> Unit = {}) {
    Row(modifier = Modifier.height(Dp(40f))) {
        Button(onClick = onStart) {
            Text("録音開始")
        }
        Button(onClick = onStop) {
            Text("録音停止")
        }
    }
}

@Preview
@Composable
fun RecordButtonPreview() {
    RecordButtons()
}

@Preview
@Composable
fun MemoDetailPreview() {
    Surface {
        MemoDetail(
            navControllerWrapper = NavControllerWrapperForPreview(),
            recordButtons = { RecordButtons() },
            state = MemoDetailScreenState.INITIAL,
            onSave = {}
        )
    }
}