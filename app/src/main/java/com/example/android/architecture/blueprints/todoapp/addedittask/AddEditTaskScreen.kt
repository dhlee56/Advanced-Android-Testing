package com.example.android.architecture.blueprints.todoapp.addedittask

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.EventObserver
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(taskId: String?,
   onNavigateToMainScreen: () -> Unit
) {
    val viewModel: AddEditTaskViewModel = viewModel()
    viewModel.start(taskId)
    var title by remember { mutableStateOf("")}
    var description by remember { mutableStateOf("")}
    var dataLoading by remember { mutableStateOf(false)}
    var snackbar by remember { mutableStateOf(R.string.no_message) }
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
    viewModel.dataLoading.observe(LocalLifecycleOwner.current) { value ->
        value?.let {
            dataLoading = it
        }
    }
    viewModel.taskUpdatedEvent.observe(LocalLifecycleOwner.current, EventObserver {
        onNavigateToMainScreen()
    })
    viewModel.snackbarText.observe(LocalLifecycleOwner.current, EventObserver { value ->
        value.let {
            snackbar = it
        }
    })
    val snackBarHostState = remember { SnackbarHostState() }
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
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveTask()
                    //onNavigateToMainScreen()
                }
            ) {
               Icon(Icons.Filled.Check, "Floating action button")
            }
        }
    ) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        LaunchedEffect(snackbar) {
            if (snackbar!=R.string.no_message) coroutineScope.launch {
                Log.d("KOTLINCLASS", "coroutineScope.launch")
                snackBarHostState.showSnackbar(
                    message = context.getString(snackbar),
                    duration = SnackbarDuration.Short,
                    actionLabel = "label",
                    withDismissAction = true,
                )
                viewModel.onSnackbarShown()
            }
        }
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.TopStart,
        ) {
            if (dataLoading) CircularProgressIndicator(
                modifier = Modifier.align(Alignment.TopStart),
                color = Color.Red
            )
            else Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
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

}