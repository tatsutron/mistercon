package com.tatsutron.remote.fragment

import androidx.fragment.app.FragmentActivity
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.util.FragmentMaker

class FragmentStateAdapter(
    activity: FragmentActivity,
) : androidx.viewpager2.adapter.FragmentStateAdapter(activity) {

    // Needs to be kept in sync with the navigation menu
    private val items = listOf(
        FragmentMaker.platformList(Platform.Category.CONSOLE),
        FragmentMaker.gameList(Platform.ARCADE),
        FragmentMaker.platformList(Platform.Category.HANDHELD),
        FragmentMaker.favoriteList(),
        FragmentMaker.system(),
    )

    override fun getItemCount() = items.size

    override fun createFragment(position: Int) = items[position]
}
