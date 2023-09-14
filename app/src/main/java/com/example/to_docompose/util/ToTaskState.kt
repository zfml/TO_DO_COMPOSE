package com.example.to_docompose.util

import com.example.to_docompose.data.models.Priority

data class ToDoTaskState (
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW
)
