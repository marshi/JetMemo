package dev.marshi.jetmemo.ui.memolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dev.marshi.jetmemo.domain.entity.Memo
import dev.marshi.jetmemo.domain.entity.MemoId
import dev.marshi.jetmemo.ui.Screen
import dev.marshi.jetmemo.ui.common.navigateToMemoDetail
import dev.marshi.jetmemo.ui.theme.JetMemoTheme

@Composable
fun MemoListScreen(
    navController: NavHostController,
    viewModel: MemoListViewModel = hiltViewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigateToMemoDetail(null) }
            )
        }
    ) {
        val state: MemoListScreenState by viewModel.state.collectAsState()
        MemoList(
            state = state,
            onNavigateToDetail = { memoId -> navController.navigateToMemoDetail(memoId) },
        )
    }
}

@Composable
fun FloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(onClick = onClick) {
        Icon(Icons.Filled.Add, contentDescription = "追加")
    }
}

// stateless
@Composable
fun MemoList(
    state: MemoListScreenState,
    onNavigateToDetail: (MemoId) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            MemoSection(state.memos.size)
        }
        state.memos.forEach { memo ->
            item {
                MemoLine(memo, modifier = Modifier.clickable {
                    onNavigateToDetail(memo.id)
                })
            }
        }
    }
}

@Composable
fun MemoSection(size: Int) {
    Text("${size}件のメモ", Modifier.padding(start = 12f.dp, top = 8f.dp, bottom = 8f.dp))
}

@Composable
fun MemoLine(
    memo: Memo,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .height(40f.dp)
            .fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 12f.dp)
        ) {
            Text(modifier = Modifier.wrapContentHeight(), text = memo.textOrDefault)
        }
    }
}

@Preview
@Composable
fun MemoLinePreview() {
    MemoLine(memo = Memo(MemoId.from(1), "aiueoaiuo"))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val memos: List<Memo> = (1..10).map { index ->
        Memo(MemoId.from(0), "memo_line_$index")
    }
    val state = MemoListScreenState(memos)
    JetMemoTheme {
        MemoListScreen(rememberNavController(), FakeMemoListViewModel(state))
    }
}