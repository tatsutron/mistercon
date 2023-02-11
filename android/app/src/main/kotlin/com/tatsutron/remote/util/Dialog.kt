package com.tatsutron.remote.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.tatsutron.remote.R
import com.tatsutron.remote.model.Config

object Dialog {

    @SuppressLint("CheckResult")
    fun connectionFailed(context: Context) {
        MaterialDialog(context).show {
            title(
                res = R.string.failed_to_connect,
            )
            message(
                res = R.string.failed_to_connect_message,
            )
            negativeButton(
                res = R.string.set_ip_address,
                click = {
                    MaterialDialog(context).show {
                        title(
                            res = R.string.enter_mister_ip_address,
                        )
                        negativeButton(R.string.cancel)
                        positiveButton(R.string.ok)
                        input(
                            inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS,
                            prefill = Persistence.getConfig()?.host,
                            callback = { _, text ->
                                Persistence.saveConfig(
                                    Config(host = text.toString()),
                                )
                            },
                        )
                    }
                },
            )
            positiveButton(R.string.ok)
        }
    }

    fun error(
        context: Context,
        throwable: Throwable,
    ) = MaterialDialog(context).show {
        title(res = R.string.error)
        message(text = throwable.toString())
        positiveButton(R.string.ok)
    }
}
