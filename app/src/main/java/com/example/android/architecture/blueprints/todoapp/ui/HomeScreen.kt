package com.example.android.architecture.blueprints.todoapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToEditScreen: (String?) -> Unit,
    onNavigateToDetailScreen: (String?) -> Unit
) {
    val viewModel: TasksViewModel = viewModel()
    var openViewDialog by remember { mutableStateOf(false) }
    var openModal by remember { mutableStateOf(false) }
    var openModalA by remember { mutableStateOf(false) }

    var tasks by remember { mutableStateOf<List<Task>>(emptyList())}

    viewModel.items.observe(LocalLifecycleOwner.current) { value ->
        value?.let {
            tasks = it
        }
    }
    fun updateOpenModal(modal: Boolean): Unit {
        openModal = modal
    }

    fun updateOpenModalA(modal: Boolean): Unit {
        openModalA = modal
    }

    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent()
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Advanced Android Testing") },
                    navigationIcon = {
                        IconButton(onClick = {
                            //openViewDialog = true
                            scope.launch {
                                drawerState.open()
                            }
                        }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "Filter Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { openModal = !openModal }) {
                            Image(
                                painter = painterResource(id = R.drawable.vertical_menu),
                                contentDescription = "Filter plants",
                                modifier = Modifier.background(Green)
                            )
                        }
                        IconButton(onClick = { openModalA = !openModalA }) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_filter_list_24dp),
                                contentDescription = "Filter plants"
                            )
                        }

                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Green
                    )
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Insert", fontSize = 30.sp) },
                    icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                    onClick = {
                        onNavigateToEditScreen(null)
                    }
                )
            }
        )
        { innerPadding ->
            Column() {
                Text(
                    "All Tasks",
                    modifier = Modifier.padding(innerPadding)
                )
                LazyColumn() {
                    items(tasks) { task: Task ->
                        Row(Modifier.clickable {
                            println("KOTLINCLASS: task detail ${task.id}")
                            onNavigateToDetailScreen(task.id)
                        }) {
                            CustomCheckbox(task.isCompleted) {
                                viewModel.completeTask(task, it)
                            }
                            Text(task.title)
                        }

                    }
                }
            }
            if (openViewDialog) {
                //openAlertDialog.value -> {
                AlertDialogExample(
                    onDismissRequest = { openViewDialog = false },
                    onConfirmation = {
                        openViewDialog = false
                        println("Confirmation registered") // Add logic here to handle confirmation.
                    },
                    dialogTitle = "Alert dialog example",
                    dialogText = "This is an example of an alert dialog with buttons.",
                    icon = Icons.Default.Info
                )
                //}
            }
            if (openModal) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =  Color(0Xff000000).copy(alpha = 0.5f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CustomModal(updateOpenModal = { open ->
                            updateOpenModal(open)
                        }, openModal = openModal)
                    }
                }
                return@Scaffold
            }
            if (openModalA) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =  Color(0Xff000000).copy(alpha = 0.5f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CustomModalA(updateOpenModal = { open ->
                            updateOpenModalA(open)
                        }, openModal = openModalA)
                    }
                }
                return@Scaffold
            }
        }
    }
}