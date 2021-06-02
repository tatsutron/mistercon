package com.tatsutron.remote

enum class Event {
    SYNC_COMPLETED;

    private val listeners = mutableListOf<() -> Unit>()

    fun subscribe(cb: () -> Unit) {
        listeners.add(cb)
    }

    fun fire() {
        listeners.forEach {
            it()
        }
    }
}
