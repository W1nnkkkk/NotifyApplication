package com.kirill.notifyapplication.domain

import io.reactivex.rxjava3.core.Single

interface NotifyRepository {
    fun getNotifyList() : Single<List<Notify>>
    suspend fun deleteNotify(notifyDbModel: Notify) : Int
    suspend fun addNotify(notifyDbModel: Notify) : Long
    suspend fun updateNotify(notifyDbModel: Notify) : Int
    suspend fun updateEnableStatus(id : Int, enable : Boolean)
}