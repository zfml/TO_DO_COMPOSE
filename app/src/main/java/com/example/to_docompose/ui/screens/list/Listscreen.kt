package com.example.to_docompose.ui.screens.list

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.fabBackgroundColor
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen : (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true ) {
        sharedViewModel.getAllTask()
    }

    val action by sharedViewModel.action

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    DisplaySnackBar(
        snackbarHostState = SnackbarHostState(),
        handleDatabaseAction = { sharedViewModel.handleDatabaseAction(action = action) },
        taskTitle = sharedViewModel.toDoTaskState.value.title ,
        action = action,
        scope = scope
    )

    sharedViewModel.handleDatabaseAction(action = action)


    
    Scaffold(
        snackbarHost = {
          SnackbarHost(hostState = snackbarHostState )
        }
        ,
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = { paddingValue ->
            ListContent(
                modifier = Modifier.padding(paddingValue)
                ,
                tasks = allTasks,
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
             ListFab(onFabClicked = navigateToTaskScreen)

        }
    )


}

@Composable
fun ListFab(
    onFabClicked: (taskId : Int) -> Unit
) {
    FloatingActionButton(onClick = {
        onFabClicked(-1)

    },
    containerColor = MaterialTheme.colorScheme.fabBackgroundColor

    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplaySnackBar(
    snackbarHostState: SnackbarHostState,
    handleDatabaseAction: () -> Unit,
    taskTitle: String,
    action: Action,
    scope: CoroutineScope
) {
    handleDatabaseAction()

    LaunchedEffect(key1 = action) {

        if(action != Action.NO_ACTION) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "${action.name}: $taskTitle",
                    actionLabel = "OK",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

}

@Preview
@Composable
private fun ListScreenPreview() {
//     ListScreen(navigateToTaskScreen = {})
//    ListFab(navigateToTaskScreen = {})
}
