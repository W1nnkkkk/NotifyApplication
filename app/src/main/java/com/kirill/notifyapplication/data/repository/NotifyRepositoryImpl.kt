package com.kirill.notifyapplication.data.repository

import com.kirill.notifyapplication.data.localdatasource.NotifyDatabase
import com.kirill.notifyapplication.data.mapper.NotifyMapper
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.domain.NotifyRepository
import io.reactivex.rxjava3.core.Single
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject

class NotifyRepositoryImpl : NotifyRepository {
    private val mapper : NotifyMapper = NotifyMapper()
    private val db by inject<NotifyDatabase>(NotifyDatabase::class.java)

    override fun getNotifyList(): Single<List<Notify>> {
        return Single.create { subscriber ->
            try {
                val data = db.getNotifyDao().getNotifyList().map { mapper.map(it) }
                subscriber.onSuccess(data)
            }
            catch (e : Throwable) {
                subscriber.onError(e)
            }
        }
    }

    override suspend fun deleteNotify(notifyDbModel: Notify) : Int {
        return db.getNotifyDao().deleteNotify(mapper.mapToDbModel(notifyDbModel))
    }

    override suspend fun addNotify(notifyDbModel: Notify) : Long {
        return db.getNotifyDao().addNotify(mapper.mapToDbModel(notifyDbModel))
    }

    override suspend fun updateNotify(notifyDbModel: Notify) : Int {
        return db.getNotifyDao().updateNotify(mapper.mapToDbModel(notifyDbModel))
    }

    override suspend fun updateEnableStatus(id: Int, enable: Boolean) {
        db.getNotifyDao().updateEnableStatus(id, enable)
    }
}