package com.kirill.notifyapplication.presentation.navigation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.presentation.screens.MainScreen
import com.kirill.notifyapplication.presentation.screens.NotifyScreen
import com.kirill.notifyapplication.presentation.viewmodel.NotifyViewModel

@Composable
fun SetupNavGraph(
    controller: NavHostController,
    viewModel: NotifyViewModel,
    context: Context
) {
    NavHost(navController = controller, startDestination = Routes.NotifyList.route) {
        composable(Routes.NotifyList.route) {
            MainScreen(controller, viewModel, context)
        }
        composable(Routes.AddNotify.route) {
            NotifyScreen(null, viewModel, controller, context)
        }
        composable(Routes.EditNotify.route + Routes.EditNotify.extension) { navBackStackEntry ->
            val notifyId = navBackStackEntry.arguments?.getString(Routes.EditNotify.key)?.toInt()
            val notify = viewModel.state.value?.list?.find { it.id == notifyId }
            NotifyScreen(notify, viewModel, controller, context)
        }
    }
}
