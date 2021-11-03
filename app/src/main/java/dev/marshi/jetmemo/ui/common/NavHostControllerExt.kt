package dev.marshi.jetmemo.ui.common

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.marshi.jetmemo.domain.entity.MemoId
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.Screen
import dev.marshi.jetmemo.ui.memodetail.MemoDetailScreen
import dev.marshi.jetmemo.ui.memodetail.RealMemoDetailViewModel
import dev.marshi.jetmemo.ui.memolist.MemoListScreen
import dev.marshi.jetmemo.ui.memolist.RealMemoListViewModel

fun NavHostController.navigateToMemoDetail(id: MemoId) {
    navigate("${Screen.MemoDetail.route}/${id.value}")
}

fun NavGraphBuilder.navGraph(
    navController: NavHostController
) {
    composable(Screen.MemoList.route) {
        MemoListScreen(navController, hiltViewModel<RealMemoListViewModel>())
    }
    composable(
        "${Screen.MemoDetail.route}/{memoId}",
        arguments = listOf(
            navArgument("memoId") { type = NavType.IntType }
        )
    ) {
        val memoId = requireNotNull(it.arguments).getInt("memoId").let { MemoId.from(it) }
        MemoDetailScreen(
            navController,
            hiltViewModel<RealMemoDetailViewModel>().apply { init(memoId) })
    }
}