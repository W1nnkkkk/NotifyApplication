package com.kirill.notifyapplication.presentation.navigation

sealed class Routes(val route: String) {
    data object NotifyList : Routes("notify_list")
    data object AddNotify : Routes("notify_add")
    data object EditNotify : Routes("notify_edit") {
        val extension = "/{id}"
        val key = "id"
    }
}