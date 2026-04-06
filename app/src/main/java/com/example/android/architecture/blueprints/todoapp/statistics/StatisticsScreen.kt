package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ui.DrawerContent
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(
    onNavigateToStatisticsScreen: () -> Unit,
    onNavigateToMainScreen: () -> Unit
) {
    val viewModel: StatisticsViewModel = viewModel()
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(onNavigateToStatisticsScreen,
                onNavigateToMainScreen)
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Statistics") },
                    navigationIcon = {
                        IconButton(onClick = {
                            //openViewDialog = true
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "Filter Menu"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Green
                    )
                )
            }
        ) {
                innerPadding ->
            var activeTasksPercent by remember { mutableStateOf<Float?>(null)}
            var completedTasksPercent by remember { mutableStateOf<Float?>(null)}
            viewModel.activeTasksPercent.observe(LocalLifecycleOwner.current) { value ->
                value?.let {
                    activeTasksPercent = it
                }
            }
            viewModel.completedTasksPercent.observe(LocalLifecycleOwner.current) { value ->
                value?.let {
                    completedTasksPercent = it
                }
            }
            Column(Modifier.padding(innerPadding)) {
                Text("Active tasks: ${activeTasksPercent}")
                Text("Completed tasks ${completedTasksPercent}")

            }
        }
    }

}