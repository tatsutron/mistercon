package com.tatsutron.remote.util

import android.os.Bundle
import com.tatsutron.remote.fragment.*

object FragmentMaker {

    const val KEY_CONSOLE = "KEY_CONSOLE"
    const val KEY_PATH = "KEY_PATH"
    const val KEY_URL = "KEY_URL"

    fun console(core: String) = ConsoleFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_CONSOLE, core)
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

    fun scriptList() = ScriptListFragment()
}
