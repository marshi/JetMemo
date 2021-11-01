package dev.marshi.jetmemo.ui.memolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dev.marshi.jetmemo.domain.entity.Memo
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.NavControllerWrapperForPreview
import dev.marshi.jetmemo.ui.Screen
import dev.marshi.jetmemo.ui.player.Player
import dev.marshi.jetmemo.ui.player.PlayerScreen
import dev.marshi.jetmemo.ui.theme.JetMemoTheme
import dev.marshi.jetmemo.utils.collectInLaunchedEffect

@Composable
fun MemoListScreen(
    navController: NavControllerWrapper,
    viewModel: MemoListViewModel = hiltViewModel()
) {
    viewModel.effect.collectInLaunchedEffect { effect ->
        when (effect) {
            is MemoListEffect.NavigateToDetail -> navController.navigate(Screen.MemoDetail.route)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.navigateToDetail() })
        }
    ) {
        MemoList(
            navController,
            viewModel
        )
    }
}

@Composable
fun FloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Add, contentDescription = "追加")
    }
}

@Composable
fun MemoList(
    navController: NavControllerWrapper,
    viewModel: MemoListViewModel
) {
    val state by viewModel.state.collectAsState()
    LazyColumn {
        state.memos.forEach { memo ->
            item {
                MemoLine(memo, modifier = Modifier.clickable {
                    navController.navigate(
                        Screen.MemoDetail.route
                    )
                })
            }
        }
    }
}

@Composable
fun MemoLine(
    memo: Memo,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = memo.textOrDefault)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetMemoTheme {
        MemoListScreen(NavControllerWrapperForPreview())
    }
}