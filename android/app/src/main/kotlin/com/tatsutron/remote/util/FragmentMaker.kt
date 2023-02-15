package com.tatsutron.remote.util

import android.os.Bundle
import com.tatsutron.remote.fragment.*
import com.tatsutron.remote.model.Platform

object FragmentMaker {

    const val KEY_PATH = "KEY_PATH"
    const val KEY_PLATFORM = "KEY_PLATFORM"
    const val KEY_PLATFORM_CATEGORY = "KEY_PLATFORM_CATEGORY"
    const val KEY_SHOW_TOOLBAR = "KEY_SHOW_TOOLBAR"
    const val KEY_URL = "KEY_URL"

    fun credits() = CreditsFragment()

    fun favoriteList() = FavoriteListFragment()

    fun game(path: String) = GameFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_PATH, path)
        }
    }

    fun gameList(platform: Platform, showToolbar: Boolean = true) = GameListFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_PLATFORM, platform.name)
            putBoolean(KEY_SHOW_TOOLBAR, showToolbar)
        }
    }

    fun image(url: String?) = ImageFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_URL, url)
        }
    }

    fun platformList(category: Platform.Category) = PlatformListFragment().apply {
        arguments = Bundle().apply {
            putString(KEY_PLATFORM_CATEGORY, category.name)
        }
    }

    fun scan() = ScanFragment()

    fun system() = SystemFragment()
}
