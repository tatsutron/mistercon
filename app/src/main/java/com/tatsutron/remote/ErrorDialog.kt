package com.tatsutron.remote

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object ErrorDialog {

    fun show(context: Context, throwable: Throwable, cb: () -> Unit = {}) {
        MaterialAlertDialogBuilder(context, R.style.AlertDialog)
            .setTitle(context.getString(R.string.error))
            .setMessage(throwable.toString())
            .setPositiveButton(context.getString(R.string.ok))
            { _: DialogInterface, _: Int ->
                cb()
            }
            .show()
    }
}
