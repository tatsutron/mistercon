package com.tatsutron.remote

import android.os.Bundle

object FragmentMaker {

    const val KEY_ID = "KEY_ID"
    const val KEY_URL = "KEY_URL"

    fun game(id: Long) = GameFragment().apply {
        arguments = Bundle().apply {
            putLong(KEY_ID, id)
        }
    }

    fun image(url: String?) = ImageFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_URL, url)
        }
    }
}
