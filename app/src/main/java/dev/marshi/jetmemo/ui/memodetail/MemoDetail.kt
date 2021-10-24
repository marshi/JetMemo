package dev.marshi.jetmemo.ui.memodetail

import android.support.v4.media.session.MediaControllerCompat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.NavControllerWrapperForPreview
import dev.marshi.jetmemo.utils.extractActivity

@Composable
fun MemoDetailScreen(
    navControllerWrapper: NavControllerWrapper,
    viewModel: MemoDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val activity = LocalContext.current.extractActivity()

    Surface {
        MemoDetail(
            navControllerWrapper,
            recordButtons = {
                RecordButtons(
                    onStart = { viewModel.startRecording("file") },
                    onStop = { viewModel.stopRecording() }
                )
            },
            player = {
                Player(
                    onStart = {
                        MediaControllerCompat.getMediaController(activity).transportControls.play()
                    }, onStop = {
                        MediaControllerCompat.getMediaController(activity).transportControls.stop()
                    }, onPause = {
                        MediaControllerCompat.getMediaController(activity).transportControls.pause()
                    }
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
    player: @Composable () -> Unit,
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
        player()
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
            player = { Player(onStart = {}, onStop = {}, onPause = {}) },
            state = MemoDetailScreenState.INITIAL,
            onSave = {}
        )
    }
}