package com.tatsutron.remote.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tatsutron.remote.R
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator

class MainFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.scan -> {
                Navigator.showScreen(
                    activity as AppCompatActivity,
                    FragmentMaker.scan(),
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        val bottomNavigation = view
            .findViewById<BottomNavigationView>(R.id.bottom_navigation)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.title = bottomNavigation.menu.getItem(0).title
        }
        viewPager.adapter = FragmentStateAdapter(requireActivity())
        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val item = bottomNavigation.menu.getItem(position)
                    bottomNavigation.selectedItemId = item.itemId
                    (activity as? AppCompatActivity)?.apply {
                        setSupportActionBar(
                            view.findViewById(R.id.toolbar)
                        )
                        supportActionBar?.title = item.title
                    }
                }
            }
        )
        bottomNavigation.setOnItemSelectedListener { item ->
            for (i in 0 until bottomNavigation.menu.size()) {
                if (bottomNavigation.menu.getItem(i) == item) {
                    viewPager.currentItem = i
                    (activity as? AppCompatActivity)?.apply {
                        setSupportActionBar(
                            view.findViewById(R.id.toolbar)
                        )
                        supportActionBar?.title = item.title
                    }
                }
            }
            true
        }
    }
}
