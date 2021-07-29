package com.tatsutron.remote

import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object Coroutine : CoroutineScope by MainScope() {

    fun launch(
        activity: Activity,
        run: () -> Unit,
        success: (() -> Unit)? = null,
        failure: (() -> Unit)? = null,
    ) {
        launch(Dispatchers.IO) {
            runCatching {
                run()
            }.onSuccess {
                activity.runOnUiThread {
                    success?.invoke()
                }
            }.onFailure {
                activity.runOnUiThread {
                    failure?.invoke()
                    Dialog.error(activity, it)
                }
            }
        }
    }
}
