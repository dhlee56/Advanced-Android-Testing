package com.example.android.architecture.blueprints.todoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.addedittask.AddEditTaskScreen
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.statistics.StatisticsScreen
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailScreen
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import com.example.android.architecture.blueprints.todoapp.ui.theme.AdvancedAndroidTestingTheme
import kotlinx.coroutines.launch
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
            HomeScreen(
                onNavigateToEditScreen = { it ->
                    navController.navigate(Edit(taskId=it)) // Navigate using the type-safe Profile object
                },
                onNavigateToDetailScreen = {
                    navController.navigate(Detail(taskId=it))
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

