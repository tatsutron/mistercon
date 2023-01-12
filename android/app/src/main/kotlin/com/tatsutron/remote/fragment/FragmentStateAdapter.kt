package com.tatsutron.remote.fragment

import androidx.fragment.app.FragmentActivity
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.util.FragmentMaker

class FragmentStateAdapter(
    activity: FragmentActivity,
) : androidx.viewpager2.adapter.FragmentStateAdapter(activity) {

    private val items = listOf(
        ConsoleListFragment(),
        FragmentMaker.gameList(Platform.ARCADE),
        FavoriteListFragment(),
        SystemFragment(),
    )

    override fun getItemCount() = items.size

    override fun createFragment(position: Int) = items[position]
}
