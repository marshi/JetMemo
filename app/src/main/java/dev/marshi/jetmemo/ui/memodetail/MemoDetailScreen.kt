package dev.marshi.jetmemo.ui.memodetail

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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.marshi.jetmemo.domain.entity.MemoId
import dev.marshi.jetmemo.ui.player.FakePlayerViewModel
import dev.marshi.jetmemo.ui.player.PlayerScreen
import dev.marshi.jetmemo.ui.player.PlayerState
import dev.marshi.jetmemo.ui.player.providePlayerViewModel

@Composable
fun MemoDetailScreen(
    navController: NavHostController,
    viewModel: MemoDetailViewModel
) {
    val state by viewModel.state.collectAsState()
    Surface {
        MemoDetail(
            state,
            recordButtons = {
                RecordButtons(
                    onStart = {
                        viewModel.dispatch(MemoDetailViewModel.Event.StartRecording("file"))
                    },
                    onStop = {
                        viewModel.dispatch(MemoDetailViewModel.Event.StopRecording)
                    }
                )
            },
            onSave = { memoId, text ->
                viewModel.dispatch(MemoDetailViewModel.Event.SaveMemo(memoId, text = text))
            },
            onValueChanged = {
                viewModel.dispatch(MemoDetailViewModel.Event.ChangeText(it))
            },
            onBackNavigation = {
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun MemoDetail(
    state: MemoDetailScreenState,
    recordButtons: @Composable () -> Unit,
    onSave: (MemoId?, String) -> Unit,
    onValueChanged: (String) -> Unit,
    onBackNavigation: () -> Unit,
) {
    Column {
        TopAppBar(
            title = { Text("title") },
            navigationIcon = {
                IconButton(onClick = {
                    onBackNavigation()
                }) {
                    Icon(Icons.Filled.ArrowBack, null)
                }
            },
            actions = {
                TextButton(onClick = { onSave(state.memoId, state.text) }) {
                    Text("保存", color = Color.Black)
                }
            },
        )
        recordButtons()
        TextField(
            value = state.text,
            onValueChange = { onValueChanged(it) },
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
    CompositionLocalProvider(
        providePlayerViewModel { FakePlayerViewModel(playerState = PlayerState()) }
    ) {
        Surface {
            MemoDetail(
                recordButtons = { RecordButtons() },
                state = MemoDetailScreenState.INITIAL,
                onSave = { _, _ -> },
                onValueChanged = {},
                onBackNavigation = {}
            )
        }
    }
}