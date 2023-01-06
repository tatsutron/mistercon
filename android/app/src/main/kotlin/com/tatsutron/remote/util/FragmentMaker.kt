package com.tatsutron.remote.util

import android.os.Bundle
import com.tatsutron.remote.fragment.*
import com.tatsutron.remote.model.Platform

object FragmentMaker {

    const val KEY_PATH = "KEY_PATH"
    const val KEY_PLATFORM = "KEY_PLATFORM"
    const val KEY_URL = "KEY_URL"

    fun console(platform: Platform) = ConsoleFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_PLATFORM, platform.name)
        }
    }

    fun credits() = CreditsFragment()

    fun game(path: String) = GameFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_PATH, path)
        }
    }

    fun image(url: String?) = ImageFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_URL, url)
        }
    }

    fun scan() = ScanFragment()
}
