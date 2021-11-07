package dev.marshi.jetmemo.ui.memodetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.marshi.jetmemo.R
import dev.marshi.jetmemo.ui.common.ToastUtils
import dev.marshi.jetmemo.ui.memodetail.MemoDetailViewModel.Event
import dev.marshi.jetmemo.ui.player.FakePlayerViewModel
import dev.marshi.jetmemo.ui.player.PlayerScreen
import dev.marshi.jetmemo.ui.player.PlayerState
import dev.marshi.jetmemo.ui.player.providePlayerViewModel
import dev.marshi.jetmemo.ui.theme.JetMemoTheme
import dev.marshi.jetmemo.ui.theme.Shapes
import dev.marshi.jetmemo.utils.collectInLaunchedEffect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MemoDetailScreen(
    navController: NavHostController,
    viewModel: MemoDetailViewModel
) {
    val context = LocalContext.current
    viewModel.effect.collectInLaunchedEffect { effect ->
        when (effect) {
            MemoDetailViewModel.Effect.ShowSaveToast -> {
                ToastUtils.showLongToast(context, "保存が完了しました")
            }
        }
    }

    val state by viewModel.state.collectAsState()
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetContent = {
            Text(modifier = Modifier.height(56.dp), text = "aaaaaaaaaaaaaaaaa")
        },
        scaffoldState = bottomSheetState,
        sheetShape = Shapes.large,
        sheetPeekHeight = 0.dp,
    ) {
        MemoDetail(
            state,
            recordButtons = {
                RecordButtons(
                    onStart = { viewModel.dispatch(Event.StartRecording("file")) },
                    onStop = { viewModel.dispatch(Event.StopRecording) }
                )
            },
            onSave = { viewModel.dispatch(Event.SaveMemo()) },
            onValueChanged = { viewModel.dispatch(Event.ChangeText(it)) },
            onBackNavigation = { navController.popBackStack() },
            onToggleBottomSheet = {
                scope.launch {
                    if (bottomSheetState.bottomSheetState.isExpanded) {
                        bottomSheetState.bottomSheetState.collapse()
                    } else {
                        bottomSheetState.bottomSheetState.expand()
                    }
                }
            }
        )
    }
}

@Composable
fun MemoDetail(
    state: MemoDetailScreenState,
    recordButtons: @Composable () -> Unit,
    onSave: () -> Unit,
    onValueChanged: (String) -> Unit,
    onBackNavigation: () -> Unit,
    onToggleBottomSheet: () -> Unit,
) {
    Surface {
        Column {
            TopAppBar(
                title = { Text("title") },
                navigationIcon = {
                    IconButton(onClick = { onBackNavigation() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = { onSave() }) {
                        Icon(painterResource(id = R.drawable.baseline_save_24), "save")
                    }
                    IconButton(onClick = { onToggleBottomSheet() }) {
                        Icon(Icons.Default.Add, null)
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
fun MemoDetailScreenPreview() {
    val state = MemoDetailScreenState(
        null, "aiueoaiueo"
    )
    CompositionLocalProvider(
        providePlayerViewModel { FakePlayerViewModel(playerState = PlayerState()) }
    ) {
        JetMemoTheme {
            MemoDetailScreen(
                navController = rememberNavController(),
                viewModel = FakeMemoDetailViewModel(state)
            )
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
                state = MemoDetailScreenState.INITIAL,
                recordButtons = { RecordButtons() },
                onSave = {},
                onValueChanged = {},
                onBackNavigation = {}
            ) {}
        }
    }
}