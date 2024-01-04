package com.anadolstudio.chronos.base.viewmodel

import com.anadolstudio.core.presentation.event.SingleMessageSnack
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.anadolstudio.core.viewmodel.livedata.SingleLiveEvent
import com.anadolstudio.core.viewmodel.livedata.onNext

interface BaseViewModelDelegate {
    fun showTodo(text: String? = null)

    class Delegate(private val singleLiveEvent: SingleLiveEvent<SingleEvent>) : BaseViewModelDelegate {

        private val todoMessages = listOf(
                "Извините, этот функционал пока не реализован \uD83D\uDE43",
                "Мы работаем над этим функционалом, следите за обновлениями \uD83D\uDC40",
                "Эта функция будет доступна в ближайшее время \uD83C\uDFC3",
        )

        override fun showTodo(text: String?) {
            val message = text ?: todoMessages.random()
            singleLiveEvent.onNext(SingleMessageSnack.Short(message))
        }
    }

}
