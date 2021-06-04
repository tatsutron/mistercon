package com.tatsutron.remote

enum class Event {
    SYNC;

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
