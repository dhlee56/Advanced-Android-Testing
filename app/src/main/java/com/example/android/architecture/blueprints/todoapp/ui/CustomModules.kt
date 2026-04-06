package com.example.android.architecture.blueprints.todoapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.tasks.TasksFilterType
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel

@Composable
fun CustomModal(updateOpenModal: (Boolean) -> Unit, openModal: Boolean) {
    val viewModel: TasksViewModel = viewModel()
    Surface(
        color = Green,
        shape = RoundedCornerShape(10.dp) // 원하는 만큼 라운드코너
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("All", modifier = Modifier.clickable {
                viewModel.setFiltering(TasksFilterType.ALL_TASKS)
                updateOpenModal(!openModal)
            })
            Text("Active", modifier = Modifier.clickable {
                viewModel.setFiltering(TasksFilterType.ACTIVE_TASKS)
                updateOpenModal(!openModal)
            })
            Text("Completed", modifier = Modifier.clickable {
                viewModel.setFiltering(TasksFilterType.COMPLETED_TASKS)
                updateOpenModal(!openModal)
            })
        }
    }
}
@Composable
fun CustomModalA(updateOpenModal: (Boolean) -> Unit, openModal: Boolean) {
    val viewModel: TasksViewModel = viewModel()
    Surface(
        color = Green,
        shape = RoundedCornerShape(10.dp) // 원하는 만큼 라운드코너
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Clear completed",
                modifier = Modifier.clickable {
                    viewModel.clearCompletedTasks()
                    updateOpenModal(!openModal)
                })
            Text("Refresh",
                modifier = Modifier.clickable {
                    viewModel.loadTasks(true)
                    updateOpenModal(!openModal)
                })
        }
    }
}
@Composable
fun CustomCheckbox(
    isChecked: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(RoundedCornerShape(8.dp)) // Custom shape
            .background(if (isChecked) Color.Yellow else White)
            .border(2.dp, Color.Yellow, RoundedCornerShape(8.dp))
            .clickable { onValueChange(!isChecked) },
        contentAlignment = Alignment.Center
    ) {
        if (isChecked) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black)
        }
    }
}