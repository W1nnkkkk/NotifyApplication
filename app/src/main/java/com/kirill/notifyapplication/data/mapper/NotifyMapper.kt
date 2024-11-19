package com.kirill.notifyapplication.data.mapper

import com.kirill.notifyapplication.data.entities.NotifyDbModel
import com.kirill.notifyapplication.domain.Notify
import kotlinx.datetime.LocalTime

class NotifyMapper {
    fun map(notifyDbModel: NotifyDbModel) : Notify {
        return Notify(
            id = notifyDbModel.id,
            name = notifyDbModel.name,
            text = notifyDbModel.text,
            enable = notifyDbModel.enable,
            time = LocalTime(notifyDbModel.timeHour, notifyDbModel.timeMinute)
        )
    }

    fun mapToDbModel(notify: Notify) : NotifyDbModel {
        return NotifyDbModel(
            id = notify.id,
            name = notify.name,
            text = notify.text,
            enable = notify.enable,
            timeHour = notify.time.hour,
            timeMinute = notify.time.minute
        )
    }
}