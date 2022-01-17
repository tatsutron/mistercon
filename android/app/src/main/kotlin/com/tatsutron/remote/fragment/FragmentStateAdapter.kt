package com.tatsutron.remote.fragment

import androidx.fragment.app.FragmentActivity

class FragmentStateAdapter(
    activity: FragmentActivity,
) : androidx.viewpager2.adapter.FragmentStateAdapter(activity) {

    private val items = listOf(
        ConsoleListFragment(),
        ArcadeListFragment(),
        FavoriteListFragment(),
        SystemFragment(),
    )

    override fun getItemCount() = items.size

    override fun createFragment(position: Int) = items[position]
}
