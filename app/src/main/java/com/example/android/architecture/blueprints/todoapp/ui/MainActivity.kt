package com.example.android.architecture.blueprints.todoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskScreen
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsScreen
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailScreen
import com.example.android.architecture.blueprints.todoapp.tasks.TasksScreen
import com.example.android.architecture.blueprints.todoapp.ui.theme.AdvancedAndroidTestingTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    //private val viewModel by viewModels<TasksViewModel>()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdvancedAndroidTestingTheme {
                MainScreen()
            }

        }
    }
}

@Serializable
object Home

@Serializable
data class Edit(val taskId: String?)


@Serializable
data class Detail(val taskId: String?)

@Serializable
object Stat

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Home) { // Use the type-safe Home object
        composable<Home> {
            TasksScreen(
                onNavigateToEditScreen = { it ->
                    navController.navigate(Edit(taskId = it)) // Navigate using the type-safe Profile object
                },
                onNavigateToDetailScreen = {
                    navController.navigate(Detail(taskId = it))
                },
                onNavigateToStatisticsScreen = {
                    navController.navigate(Stat)
                },
                onNavigateToMainScreen = {
                    navController.navigate(Home)
                }
            )
        }
        composable<Stat> {
            StatisticsScreen(
                onNavigateToStatisticsScreen = {
                    navController.navigate(Stat)
                },
                onNavigateToMainScreen = {
                    navController.navigate(Home)
                }
            )
        }
        composable<Edit> { backStackEntry ->
            // Retrieve arguments in the destination
            val edit = backStackEntry.toRoute<Edit>()
            AddEditTaskScreen(taskId = edit.taskId,
                onNavigateToMainScreen = {
                    navController.navigate(Home)
                }
            )
        }
        composable<Detail> { backStackEntry ->
            val detail = backStackEntry.toRoute<Detail>()
            TaskDetailScreen (
                taskId = detail.taskId,
                onNavigateToMainScreen = {
                    navController.navigate(Home)
                },
                onNavigateToEditScreen = {
                    navController.navigate(Edit(detail.taskId))
                }
            )
        }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavGraph(
        navController = navController,
    )
}

