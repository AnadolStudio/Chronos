package com.anadolstudio.chronos.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

open class SimpleScrollListener(
        private val onScrolled: ((recycler: RecyclerView, dx: Int, dy: Int, state: Int) -> Unit)? = null,
        private val onScrollStateChanged: ((recycler: RecyclerView, state: Int) -> Unit)? = null
) : RecyclerView.OnScrollListener() {

    private var currentState: Int = SCROLL_STATE_IDLE

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        onScrolled?.invoke(recyclerView, dx, dy, currentState)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        currentState = newState
        onScrollStateChanged?.invoke(recyclerView, newState)
    }
}
