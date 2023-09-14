package com.example.to_docompose.di.navigation.destination

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.ui.screens.list.ListScreen
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import com.example.to_docompose.util.Constants.LIST_SCREEN
import com.example.to_docompose.util.toAction

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen : (taskId : Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
     composable(
         route = LIST_SCREEN,
         arguments = listOf( navArgument(LIST_ARGUMENT_KEY) {
             type = NavType.StringType
         })
     ) { navBackStackEntry ->
         val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
         LaunchedEffect(key1 = action) {
             sharedViewModel.handleDatabaseAction(action = action)
         }
         ListScreen(
             navigateToTaskScreen = navigateToTaskScreen,
             sharedViewModel = sharedViewModel
         )
     }

}


