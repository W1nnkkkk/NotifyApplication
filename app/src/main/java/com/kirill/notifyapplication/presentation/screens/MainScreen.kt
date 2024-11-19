package com.kirill.notifyapplication.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kirill.notifyapplication.R
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.presentation.items.NotifyList
import com.kirill.notifyapplication.presentation.navigation.Routes
import com.kirill.notifyapplication.presentation.viewmodel.NotifyViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: NotifyViewModel,
    context: Context
) {
    Column {
        Spacer(Modifier.padding(top = 10.dp))
        NotifyList(navController, viewModel, context)
        Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxSize()) {
            Button (onClick = {
                navController.navigate(Routes.AddNotify.route)
            }, modifier = Modifier.padding(10.dp)) {
                Icon(painter = painterResource(R.drawable.ic_add),
                    contentDescription = "ImageButton",
                    modifier = Modifier.size(35.dp)
                )
            }
        }

    }
}