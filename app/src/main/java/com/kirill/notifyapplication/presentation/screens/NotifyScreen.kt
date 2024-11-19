package com.kirill.notifyapplication.presentation.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirill.notifyapplication.R
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.presentation.TimeObject
import com.kirill.notifyapplication.presentation.theme.BackgroundColor
import com.kirill.notifyapplication.presentation.viewmodel.NotifyViewModel
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotifyScreen (
    notify: Notify?,
    viewModel: NotifyViewModel,
    navController: NavController,
    context: Context
) {
    var text by remember { mutableStateOf(notify?.text ?: "") }
    var name by remember { mutableStateOf(notify?.name ?: "") }
    var textColor by remember { mutableStateOf(TextFieldColorsObj.standartBorderColor) }
    var nameColor by remember { mutableStateOf(TextFieldColorsObj.standartBorderColor) }
    val timePickerState = rememberTimePickerState(
        initialHour = notify?.time?.hour ?: TimeObject.getTimeNow().hour,
        initialMinute = notify?.time?.minute ?: TimeObject.getTimeNow().minute,
        is24Hour = true,
    )

    Column(modifier = Modifier.fillMaxSize().background(BackgroundColor)) {
        Row(modifier = Modifier.padding(5.dp)) {
           IconButton(onClick = { navController.popBackStack() }) {
               Image(painter = painterResource(R.drawable.ic_back),
                   contentDescription = "Back",
                   modifier = Modifier.size(50.dp))
           }

            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd) {
                Row {
                    IconButton(onClick = {
                        notify?.also {
                            viewModel.deleteNotify(it)
                            navController.popBackStack()
                        }
                    }) {
                        Image(painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Delete",
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = Modifier.size(50.dp))
                    }
                    Spacer(Modifier.padding(horizontal = 6.dp))
                    IconButton(onClick = {
                        if (!validateName(name)) {
                            nameColor = TextFieldColorsObj.errorBorderColor
                            return@IconButton
                        }
                        if (!validateText(text)) {
                            textColor = TextFieldColorsObj.errorBorderColor
                            return@IconButton
                        }
                        val notification = Notify(
                            id = notify?.id ?: 0,
                            name = name,
                            text = text,
                            time = LocalTime(timePickerState.hour, timePickerState.minute),
                            enable = notify?.enable ?: true
                        )
                        if (notify == null) {
                            viewModel.addNotify(notification)
                        }
                        else {
                            viewModel.updateNotify(notification)
                        }
                        navController.popBackStack()
                    }) {
                        Image(painter = painterResource(R.drawable.ic_check),
                            contentDescription = "OK",
                            modifier = Modifier.size(50.dp))
                    }
                }
            }
        }

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(bottom = 20.dp)) {
            Column {
                OutlinedTextField(value = name,
                    onValueChange = {
                        name = it.take(35)
                        nameColor = TextFieldColorsObj.standartBorderColor
                    },
                    label = { Text(text = context.getString(R.string.name_notify), fontSize = 18.sp) },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BackgroundColor,
                        unfocusedContainerColor = BackgroundColor,
                        unfocusedLabelColor = nameColor
                    ),
                    modifier = Modifier.size(width = 280.dp, height = 65.dp)
                )
                Spacer(Modifier.padding(vertical = 5.dp))
                OutlinedTextField(value = text,
                    onValueChange = {
                        text = it.take(150)
                        textColor = TextFieldColorsObj.standartBorderColor
                    },
                    label = { Text(text = context.getString(R.string.text_notify), fontSize = 18.sp) },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BackgroundColor,
                        unfocusedContainerColor = BackgroundColor,
                        unfocusedLabelColor = textColor
                    ),
                    modifier = Modifier.size(width = 280.dp, height = 65.dp)
                )
                Spacer(Modifier.padding(vertical = 10.dp))
                TimePicker(timePickerState, modifier = Modifier.padding(start = 3.dp))
            }
        }
    }
}

fun validateName(name : String) : Boolean {
    return name != ""
}

fun validateText(text : String) : Boolean {
    return text != ""
}

object TextFieldColorsObj {
    val standartBorderColor: Color = Color.DarkGray
    val errorBorderColor : Color = Color.Red
}