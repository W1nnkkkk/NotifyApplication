package com.kirill.notifyapplication.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kirill.notifyapplication.domain.Notify
import com.kirill.notifyapplication.domain.NotifyRepository
import com.kirill.notifyapplication.presentation.alarm.AlarmObject
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class NotifyViewModel(
    private val repository: NotifyRepository,
    context: Context
) : ViewModel() {

    private val _state : MutableLiveData<State> = MutableLiveData(State.Empty)
    val state : LiveData<State> = _state

    private val ref : WeakReference<Context> = WeakReference(context)

    init {
        repository.getNotifyList()
            .subscribeOn(Schedulers.newThread())
            .subscribe(Subscriber(_state))
    }

    fun getNotifyList() {
        repository.getNotifyList()
    }

    fun addNotify(notify : Notify) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = repository.addNotify(notify)
            AlarmObject.createRepeating(
                    id = id.toInt(),
                    name = notify.name,
                    text = notify.text,
                    context = ref.get()!!,
                    localTime = notify.time
                )
        }
        val list : MutableList<Notify> = _state.value!!.list.toMutableList()
        list.add(notify)
        _state.value = State.Loaded(list)
    }

    fun deleteNotify(notify : Notify) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteNotify(notify)
            AlarmObject.cancelRepeating(
                notify.id,
                ref.get()!!
            )
        }

        val list: MutableList<Notify> = _state.value!!.list.toMutableList()
        list.remove(notify)
        _state.value = State.Loaded(list)
    }

    fun updateNotify(notify : Notify) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateNotify(notify)
            AlarmObject.cancelRepeating(notify.id, ref.get()!!)
            AlarmObject.createRepeating(
                id = notify.id,
                name = notify.name,
                text = notify.text,
                context = ref.get()!!,
                localTime = notify.time
            )
        }
        val list : MutableList<Notify> = _state.value!!.list.toMutableList()
        list.remove(list.find { it.id == notify.id })
        list.add(notify)
        _state.value = State.Loaded(list)
    }

    fun updateEnabledStatus(notify: Notify, enable : Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateEnableStatus(notify.id, enable)
            if (enable) {
                AlarmObject.createRepeating(
                    id = notify.id,
                    name = notify.name,
                    text = notify.text,
                    context = ref.get()!!,
                    localTime = notify.time
                )
            }
            else {
                AlarmObject.cancelRepeating(notify.id, ref.get()!!)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Subscriber.disposable.dispose()
    }


    sealed class State(val list: List<Notify>) {
        data class Loaded(val notify: List<Notify>) : State(notify)
        data object Empty : State(emptyList())
    }

    private class Subscriber(val state : MutableLiveData<State>) : SingleObserver<List<Notify>> {
        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onError(e: Throwable) {
            e.localizedMessage?.let { Log.e("LOG", it) }
            state.postValue(State.Empty)
        }

        override fun onSuccess(t: List<Notify>) {
            state.postValue(State.Loaded(t))
        }

        companion object {
            lateinit var disposable : Disposable
        }
    }

}