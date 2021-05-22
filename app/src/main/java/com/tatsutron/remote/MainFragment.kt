package com.tatsutron.remote

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainFragment : Fragment(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
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
            R.id.download -> {
                val context = requireContext()
                item.isEnabled = false
                item.icon.setTint(context.getColor(R.color.gray_800))
                val cb = {
                    item.icon.setTint(context.getColor(R.color.white))
                    item.isEnabled = true
                }
                if (Persistence.getHost().isNotEmpty()) {
                    sync(cb)
                } else {
                    val view = layoutInflater.inflate(
                        R.layout.dialog_no_host,
                        null, // root
                    )
                    AlertDialog.Builder(requireContext()).apply {
                        setTitle(R.string.unable_to_sync)
                        setMessage(R.string.please_enter_ip_address)
                        setNegativeButton(android.R.string.cancel) { _, _ ->
                            cb()
                        }
                        setPositiveButton(android.R.string.yes) { _, _ ->
                            val host = view.findViewById<EditText>(R.id.host)
                            Persistence.saveHost(host.text.toString())
                            Event.HOST_SET.fire()
                            sync(cb)
                        }
                        setView(view)
                        setCancelable(false)
                        show()
                    }
                }
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
            setSupportActionBar(view.findViewById(R.id.main_toolbar))
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
                            view.findViewById(R.id.main_toolbar)
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
                            view.findViewById(R.id.main_toolbar)
                        )
                        supportActionBar?.title = item.title
                    }
                }
            }
            true
        }
    }

    private fun sync(cb: () -> Unit) {
        Persistence.clearGames()
        Event.SYNC_COMPLETED.fire()
        launch(Dispatchers.IO) {
            runCatching {
                Core.values().forEach {
                    it.sync()
                }
            }.onSuccess {
                requireActivity().runOnUiThread {
                    cb()
                    Event.SYNC_COMPLETED.fire()
                }
            }
        }
    }
}
