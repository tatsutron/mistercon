package com.tatsutron.remote.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
import com.afollestad.materialdialogs.DialogCallback
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.tatsutron.remote.R

object Dialog {

    fun confirm(
        context: Context,
        message: String,
        cancel: DialogCallback? = null,
        ok: DialogCallback,
    ) = MaterialDialog(context).show {
        message(text = message)
        negativeButton(res = R.string.cancel, click = cancel)
        positiveButton(res = R.string.ok, click = ok)
    }

    fun error(
        context: Context,
        throwable: Throwable,
    ) = MaterialDialog(context).show {
        title(R.string.error)
        message(text = throwable.toString())
        positiveButton(R.string.ok)
    }

    fun info(
        context: Context,
        message: String,
        ok: DialogCallback,
    ) = MaterialDialog(context).show {
        message(text = message)
        positiveButton(res = R.string.ok, click = ok)
    }

    @SuppressLint("CheckResult")
    fun input(
        context: Context,
        title: String,
        text: String,
        ok: InputCallback,
    ) = MaterialDialog(context).show {
        title(text = title)
        negativeButton(R.string.cancel)
        positiveButton(R.string.ok)
        input(
            inputType = TYPE_TEXT_FLAG_NO_SUGGESTIONS,
            prefill = text,
            callback = ok,
        )
    }
}
