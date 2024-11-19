package com.kirill.notifyapplication.data.localdatasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kirill.notifyapplication.data.entities.NotifyDbModel

@Dao
interface NotifyDao {
    @Query("SELECT * FROM notifications")
    fun getNotifyList() : List<NotifyDbModel>
    @Delete
    fun deleteNotify(notifyDbModel: NotifyDbModel) : Int
    @Insert
    fun addNotify(notifyDbModel: NotifyDbModel) : Long
    @Update(NotifyDbModel::class)
    fun updateNotify(notifyDbModel: NotifyDbModel) : Int
    @Query("UPDATE notifications SET enable = :enable  WHERE id = :id")
    fun updateEnableStatus(id : Int, enable : Boolean)
}