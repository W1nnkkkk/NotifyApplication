package com.kirill.notifyapplication.data.localdatasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kirill.notifyapplication.data.entities.NotifyDbModel
import com.kirill.notifyapplication.domain.NotifyRepository
import org.koin.core.Koin

@Database(entities = [NotifyDbModel::class], version = 2)
abstract class NotifyDatabase : RoomDatabase() {
    abstract fun getNotifyDao() : NotifyDao

    companion object {
        private var INSTANCE : NotifyDatabase? = null
        private val NAME = "notify_db"

        fun getInstance(context: Context) : NotifyDatabase{
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                        context,
                        NotifyDatabase::class.java,
                        NAME
                    ).fallbackToDestructiveMigration().build()
                INSTANCE = inst
                inst
            }
        }
    }
}