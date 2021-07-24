package com.tatsutron.remote

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object ErrorDialog {

    fun show(context: Context, throwable: Throwable, cb: () -> Unit = {}) {
        val listener = { _: DialogInterface, _: Int -> cb() }
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.sync_error))
            .setMessage(throwable.toString())
            .setPositiveButton(context.getString(R.string.ok), listener)
            .show()
    }
}
