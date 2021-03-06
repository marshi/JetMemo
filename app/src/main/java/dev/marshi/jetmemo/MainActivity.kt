package dev.marshi.jetmemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.marshi.jetmemo.ui.ProvideViewModels
import dev.marshi.jetmemo.ui.Screen
import dev.marshi.jetmemo.ui.common.navGraph
import dev.marshi.jetmemo.ui.theme.JetMemoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            JetMemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ProvideViewModels {
                        NavHost(navHostController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun NavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Screen.MemoList.route) {
        navGraph(navHostController)
    }
}

