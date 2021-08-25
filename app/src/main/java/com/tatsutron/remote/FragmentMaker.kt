package com.tatsutron.remote

import android.os.Bundle
import com.tatsutron.remote.fragment.*

object FragmentMaker {

    const val KEY_CORE = "KEY_CORE"
    const val KEY_PATH = "KEY_PATH"
    const val KEY_URL = "KEY_URL"

    fun credits() = CreditsFragment()

    fun scriptList() = ScriptListFragment()

    fun console(core: String) = ConsoleFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_CORE, core)
        }
    }

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
