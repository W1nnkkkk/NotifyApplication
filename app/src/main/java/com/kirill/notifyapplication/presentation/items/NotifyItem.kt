package com.kirill.notifyapplication.presentation.items

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.domain.NotifyRepository
import com.kirill.notifyapplication.presentation.TimeObject
import com.kirill.notifyapplication.presentation.navigation.Routes
import com.kirill.notifyapplication.presentation.viewmodel.NotifyViewModel
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalTime
import org.koin.compose.viewmodel.koinViewModel
import org.koin.java.KoinJavaComponent.inject

@Composable
fun NotifyItem(
    notify : Notify,
    notifyViewModel: NotifyViewModel,
    navController: NavController
) {
    var checked by remember { mutableStateOf(notify.enable) }

    Card(modifier = Modifier.fillMaxWidth(),
        onClick = {
            navController.navigate(Routes.EditNotify.route + "/${notify.id}")
        }
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Column(modifier = Modifier.padding(5.dp).width(170.dp)) {
                Text(text = notify.name, style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ))
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = notify.text, style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp
                ))
            }

            Spacer(modifier = Modifier.padding(2.dp))

            val str = notify.time.format(LocalTime.Format {
                hour()
                char(':')
                minute()
            })

            Text(text = str,
                modifier = Modifier.padding(top = 12.dp),
                fontSize = 22.sp,
                textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.padding(2.dp))

            Box(contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth(1f)) {
                Switch(
                    checked,
                    onCheckedChange = {
                        checked = it
                        notifyViewModel.updateEnabledStatus(notify, checked)
                    }
                )
            }

        }
    }
}