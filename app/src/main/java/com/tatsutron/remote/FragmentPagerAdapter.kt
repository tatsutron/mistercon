package com.tatsutron.remote

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentPagerAdapter(
    fragmentManager: FragmentManager,
) : FragmentPagerAdapter(fragmentManager) {

    private val items = listOf<Fragment>(
        GameListFragment(),
        SystemFragment(),
    )

    override fun getCount() = items.size

    override fun getItem(position: Int): Fragment = items[position]

    override fun getPageTitle(position: Int) = items[position].toString()
}
