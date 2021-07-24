package com.tatsutron.remote

import android.os.Bundle
import com.tatsutron.remote.fragment.*

object FragmentMaker {

    const val KEY_CORE = "KEY_CORE"
    const val KEY_ID = "KEY_ID"
    const val KEY_URL = "KEY_URL"

    fun credits() = CreditsFragment()

    fun scriptList() = ScriptListFragment()

    fun console(core: String) = ConsoleFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_CORE, core)
        }
    }

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
