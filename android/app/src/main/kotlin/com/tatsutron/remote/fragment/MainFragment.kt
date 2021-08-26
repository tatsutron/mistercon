package com.tatsutron.remote.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tatsutron.remote.FragmentMaker
import com.tatsutron.remote.FragmentPagerAdapter
import com.tatsutron.remote.Navigator
import com.tatsutron.remote.R

class MainFragment : Fragment() {

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
                Navigator.show(FragmentMaker.scan())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        val bottomNavigation = view
            .findViewById<BottomNavigationView>(R.id.bottom_navigation)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.toolbar))
            supportActionBar?.title = bottomNavigation.menu.getItem(0).title
        }
        viewPager.adapter = FragmentPagerAdapter(fragmentManager!!)
        viewPager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                }

                override fun onPageSelected(position: Int) {
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
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
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
