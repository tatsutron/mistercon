package com.tatsutron.remote.util

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
        failure: ((Throwable) -> Unit)? = null,
        finally: (() -> Unit)? = null,
    ) {
        launch(Dispatchers.IO) {
            runCatching {
                run()
            }.onSuccess {
                activity.runOnUiThread {
                    success?.invoke()
                    finally?.invoke()
                }
            }.onFailure {
                activity.runOnUiThread {
                    failure?.invoke(it) ?: Dialog.error(activity, it)
                    finally?.invoke()
                }
            }
        }
    }
}
