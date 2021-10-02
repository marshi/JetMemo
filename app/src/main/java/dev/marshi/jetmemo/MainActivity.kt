package dev.marshi.jetmemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.marshi.jetmemo.data.database.AppDatabase
import dev.marshi.jetmemo.ui.NavControllerWrapper
import dev.marshi.jetmemo.ui.NavControllerWrapperImpl
import dev.marshi.jetmemo.ui.Screen
import dev.marshi.jetmemo.ui.memodetail.MemoDetail
import dev.marshi.jetmemo.ui.memolist.MemoList
import dev.marshi.jetmemo.ui.memolist.MemoListScreen
import dev.marshi.jetmemo.ui.theme.JetMemoTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            JetMemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navHostController = navController)
                }
            }
        }
    }
}

@Composable
fun NavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.MemoList.route) {
        val navController = NavControllerWrapperImpl(navHostController)
        composable(Screen.MemoList.route) {
            MemoListScreen(navController)
        }
        composable(Screen.MemoDetail.route) {
            MemoDetail(navController)
        }
    }
}

