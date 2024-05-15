package com.example.todo.todoui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TodoUi() {
    val context = LocalContext.current
    val sharedPreferenceManager = remember {
        SharedPreferenceManager(context)
    }

    var tasks by remember { mutableStateOf(sharedPreferenceManager.tasks) }
    val textState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Todo List",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 10.dp, start = 30.dp, end = 30.dp)
                .fillMaxWidth(),
            value = textState.value,
            onValueChange = { newText -> textState.value = newText },
            label = { Text(text = "Today Task") },
            maxLines = 2,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            if (textState.value.isNotBlank()) {
                                tasks = tasks + textState.value
                                sharedPreferenceManager.tasks = tasks
                                textState.value = ""
                            }
                        }
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .background(color = Color.Black, shape = RoundedCornerShape(5.dp)),
                    tint = Color.White,
                )
            }
        )

        Spacer(modifier = Modifier.padding(10.dp))
        tasks.forEachIndexed { index, task ->
            TodoItem(name = task, onEdit = { newText ->
                val updatedTasks = tasks.toMutableList()
                updatedTasks[index] = newText
                tasks = updatedTasks
                sharedPreferenceManager.tasks = tasks
            }) {
                val updatedTasks = tasks.toMutableList()
                updatedTasks.removeAt(index)
                tasks = updatedTasks
                sharedPreferenceManager.tasks = tasks
            }
        }
    }
}

@Composable
fun TodoItem(name: String, onEdit: (String) -> Unit, onDelete: () -> Unit) {
    var editing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(name) }

    Row(
        modifier = Modifier
            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(5.dp)
            )
            .padding(vertical = 3.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (editing) {
            OutlinedTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                modifier = Modifier
                    .padding(end = 10.dp)
                    .width(200.dp),
                maxLines = 1
            )
        } else {
            Text(
                text = name,
                fontSize = 15.sp,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .width(200.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "",
            modifier = Modifier
                .clickable { onDelete() }
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(5.dp)
                )
                .background(color = Color.Black, shape = RoundedCornerShape(5.dp)),
            tint = Color.White
        )
        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
        Icon(
            imageVector = Icons.Default.Create,
            contentDescription = "",
            modifier = Modifier
                .clickable {
                    if (editing) {
                        onEdit(text)
                    }
                    editing = !editing
                }
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(5.dp)
                )
                .background(color = Color.Black, shape = RoundedCornerShape(5.dp)),
            tint = Color.White
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TodoUiPreview() {
    TodoUi()
}