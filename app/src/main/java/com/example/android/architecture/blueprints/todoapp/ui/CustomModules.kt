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
import com.example.android.architecture.blueprints.todoapp.R

@Composable
fun CustomModal(updateOpenModal: (Boolean) -> Unit, openModal: Boolean) {
    Surface(
        color = Green,
        shape = RoundedCornerShape(10.dp) // 원하는 만큼 라운드코너
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("All")
            Text("Active")
            Text("Completed")
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.End)
                    .clickable { updateOpenModal(!openModal) },
                painter = painterResource(id = R.drawable.cancel),
                contentDescription = "modal_close",
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
@Composable
fun CustomModalA(updateOpenModal: (Boolean) -> Unit, openModal: Boolean) {
    Surface(
        color = Green,
        shape = RoundedCornerShape(10.dp) // 원하는 만큼 라운드코너
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Clear completed")
            Text("Refresh")
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.End)
                    .clickable { updateOpenModal(!openModal) },
                painter = painterResource(id = R.drawable.cancel),
                contentDescription = "modal_close",
            )
            Spacer(modifier = Modifier.height(12.dp))
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