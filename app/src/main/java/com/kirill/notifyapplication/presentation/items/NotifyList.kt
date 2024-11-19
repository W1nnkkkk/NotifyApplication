package com.kirill.notifyapplication.presentation.items

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.kirill.notifyapplication.R
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.presentation.viewmodel.NotifyViewModel

@Composable
fun NotifyList(
    navController: NavController,
    viewModel: NotifyViewModel,
    context: Context
) {
    var list : List<Notify> by remember { mutableStateOf(emptyList()) }
    viewModel.state.observe(context as LifecycleOwner) {
        when(it) {
            NotifyViewModel.State.Empty -> {}
            is NotifyViewModel.State.Loaded -> list = it.list
        }
    }
    if (list.isEmpty()) {
        Text(
            text = context.getString(R.string.empty_here),
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth().padding(top = 70.dp),
            textAlign = TextAlign.Center
        )
    }
    else {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            items(list) {
                NotifyItem(
                    notify = it,
                    navController = navController,
                    notifyViewModel = viewModel
                )
                Spacer(Modifier.padding(vertical = 5.dp))
            }
        }
    }

}