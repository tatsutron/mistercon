package com.tatsutron.remote

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object Dialog {

    fun confirm(
        context: Context,
        messageId: Int,
        cancel: () -> Unit = {},
        ok: () -> Unit = {},
    ) {
        MaterialAlertDialogBuilder(context, R.style.Dialog)
            .setMessage(context.getString(messageId))
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
}
