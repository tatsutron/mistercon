package com.tatsutron.remote

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.text.InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.InputCallback
import com.afollestad.materialdialogs.input.input
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Dialog {

    fun confirm(
        context: Context,
        message: String,
        cancel: () -> Unit = {},
        ok: () -> Unit = {},
    ) {
        MaterialAlertDialogBuilder(context, R.style.Dialog)
            .setMessage(message)
            .setNegativeButton(context.getString(R.string.cancel))
            { _: DialogInterface, _: Int ->
                cancel()
            }
            .setPositiveButton(context.getString(R.string.ok))
            { _: DialogInterface, _: Int ->
                ok()
            }
            .show()
    }

    fun error(
        context: Context,
        throwable: Throwable,
        ok: () -> Unit = {},
    ) {
        MaterialAlertDialogBuilder(context, R.style.Dialog)
            .setTitle(context.getString(R.string.error))
            .setMessage(throwable.toString())
            .setPositiveButton(context.getString(R.string.ok))
            { _: DialogInterface, _: Int ->
                ok()
            }
            .show()
    }

    fun info(
        context: Context,
        message: String,
        ok: () -> Unit = {},
    ) {
        MaterialAlertDialogBuilder(context, R.style.Dialog)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok))
            { _: DialogInterface, _: Int ->
                ok()
            }
            .show()
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
