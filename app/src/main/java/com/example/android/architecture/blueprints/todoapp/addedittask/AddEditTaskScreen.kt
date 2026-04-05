package com.example.android.architecture.blueprints.todoapp.addedittask

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(taskId: String?,
   onNavigateToMainScreen: () -> Unit
) {
    val viewModel: AddEditTaskViewModel = viewModel()

    println("KOTLINCLASS: $taskId")
    viewModel.start(taskId)
    var title by remember { mutableStateOf("")}
    var description by remember { mutableStateOf("")}
    viewModel.title.observe(LocalLifecycleOwner.current) { value ->
        value?.let {
            title = it
        }
    }
    viewModel.description.observe(LocalLifecycleOwner.current) { value ->
        value?.let {
            description = it
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "New Task") },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateToMainScreen()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Green
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveTask()
                    onNavigateToMainScreen()
                }
            ) {
               Icon(Icons.Filled.Check, "Floating action button")
            }
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            TextField(
                value = title,
                onValueChange = { viewModel.title.value = it },
                label = { Text("Enter your title") }
            )
            TextField(
                value = description,
                onValueChange = { viewModel.description.value = it },
                label = { Text("Enter your decription") }
            )
        }
    }

}