package com.example.to_docompose.ui.screens.list

import android.app.ActivityManager.TaskDescription
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.to_docompose.R
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_docompose.ui.theme.TASK_ITEM_ELEVATION
import com.example.to_docompose.ui.theme.taskItemBackgroundColor
import com.example.to_docompose.ui.theme.taskItemTextColor
import com.example.to_docompose.util.RequestState

@Composable
fun ListContent(
    modifier: Modifier,
    tasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    if( tasks is RequestState.Success) {
        if(tasks.data.isEmpty()) {
            EmptyContent()
        }else {
            DisplayContent(
                modifier = modifier ,
                tasks = tasks.data ,
                navigateToTaskScreen = navigateToTaskScreen
            )

        }
    }
}

@Composable
fun DisplayContent(
    modifier: Modifier,
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = tasks,
            key = {task -> task.id}
        ) { task ->
            TaskItem(
                toDoTask = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen:(taskId: Int) -> Unit
) {
   Surface(
       modifier = Modifier
           .fillMaxWidth()
       ,
       color = MaterialTheme.colorScheme.taskItemBackgroundColor,
       shape = RectangleShape,
       tonalElevation = TASK_ITEM_ELEVATION,
       onClick = {
           navigateToTaskScreen(toDoTask.id)
       }
   ) {
       Column(
           modifier = Modifier
               .padding(all = LARGE_PADDING)
               .fillMaxWidth()
       ){
           Row{
               Text(
                   modifier = Modifier.weight(8f),
                   text = toDoTask.title,
                   color = MaterialTheme.colorScheme.taskItemTextColor,
                   style = MaterialTheme.typography.headlineMedium,
                   fontWeight = FontWeight.Bold,
                   maxLines = 1
               )
               
               Box(
                   modifier = Modifier
                       .fillMaxWidth()
                       .weight(1f),
                   contentAlignment = Alignment.TopEnd
               ) {
                   Canvas(
                       modifier = Modifier
                           .width(PRIORITY_INDICATOR_SIZE)
                           .height(PRIORITY_INDICATOR_SIZE)
                   ) {
                       drawCircle(
                           color = toDoTask.priority.color
                       )

                   }
               }
           }

           Text(
               modifier = Modifier.fillMaxWidth(),
               text = toDoTask.description,
               color = MaterialTheme.colorScheme.taskItemTextColor,
               style = MaterialTheme.typography.bodyMedium,
               maxLines = 2,
               overflow = TextOverflow.Ellipsis
           )

       }

   }
}