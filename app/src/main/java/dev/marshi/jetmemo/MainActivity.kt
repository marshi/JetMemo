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
import dev.marshi.jetmemo.ui.memodetail.MemoDetail
import dev.marshi.jetmemo.ui.memolist.MemoList
import dev.marshi.jetmemo.ui.theme.JetMemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(
                LocalNavController provides navController
            ) {
                JetMemoTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        NavHost(navHostController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun NavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "memolist") {
        composable(NavDestination.MemoList.dest) {
            MemoList()
        }
        composable(NavDestination.MemoDetail.dest) {
            MemoDetail()
        }
    }
}

enum class NavDestination(val dest: String) {
    MemoList("memolist"),
    MemoDetail("detail")
}

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("no such navController")
}