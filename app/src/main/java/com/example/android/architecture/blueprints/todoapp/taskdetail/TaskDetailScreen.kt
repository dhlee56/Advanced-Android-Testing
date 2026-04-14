package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.ui.CustomCheckbox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(taskId: String?,
    onNavigateToMainScreen: () -> Unit,
    onNavigateToEditScreen: (String?) -> Unit
) {
    val viewModel: TaskDetailViewModel = viewModel()
    viewModel.start(taskId!!)
    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text("Detail Screen")
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.deleteTask()
                        onNavigateToMainScreen()
                    }) {
                        Image(
                            painter = painterResource(id = R.drawable.trash_icon),
                            contentDescription = "Filter plants",
                            modifier = Modifier.background(Green).size(30.dp)
                        )
                    }

                },
            )
        },

        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Edit", fontSize = 30.sp) },
                icon = { Icon(Icons.Filled.Edit, contentDescription = "") },
                onClick = {
                    onNavigateToEditScreen(taskId)
                }
            )
        }
    ) { innerPadding ->
        var detailTask by remember { mutableStateOf<Task?>(null)}
        viewModel.task.observe(LocalLifecycleOwner.current) { value ->
            value?.let {
                detailTask = it
            }
        }
        detailTask?.let { task ->
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color.White)
            ) {
                Spacer(Modifier.padding(2.dp))
                CustomCheckbox(task.isCompleted) {
                    viewModel.setCompleted(it)
                }
                Spacer(Modifier.padding(2.dp))
                Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start) {
                    Text(task.title)
                    Text("${task.description}")
                }
            }
        }
    }
}