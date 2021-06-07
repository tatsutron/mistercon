package com.tatsutron.remote

import android.os.Bundle

object FragmentMaker {

    const val KEY_FILENAME = "KEY_FILENAME"
    const val KEY_URL = "KEY_URL"

    fun game(filename: String) = GameFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_FILENAME, filename)
        }
    }

    fun image(url: String?) = ImageFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_URL, url)
        }
    }
}
