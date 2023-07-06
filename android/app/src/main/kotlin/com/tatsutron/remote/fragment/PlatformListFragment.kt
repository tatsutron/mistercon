package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.tatsutron.remote.R
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.recycler.PlatformItem
import com.tatsutron.remote.recycler.PlatformListAdapter
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Persistence
import com.tatsutron.remote.util.Util


class PlatformListFragment : BaseFragment() {

    private lateinit var platformCategory: Platform.Category
    private lateinit var adapter: PlatformListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_platform_list, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_platform_list,
            container,
            false,
        )
    }

    @SuppressLint("CheckResult")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.set_ip_address -> {
                MaterialDialog(requireContext()).show {
                    title(
                        res = R.string.enter_mister_ip_address,
                    )
                    negativeButton(R.string.cancel)
                    positiveButton(R.string.ok)
                    input(
                        inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS,
                        prefill = Persistence.host,
                        callback = { _, text ->
                            Persistence.host = text.toString()
                            Navigator.showLoadingScreen()
                            Util.deployAssets(
                                activity = requireActivity(),
                                callback = {
                                    Navigator.hideLoadingScreen()
                                },
                            )
                        },
                    )
                }
                true
            }

            R.id.favorites -> {
                Navigator.showScreen(
                    activity as AppCompatActivity,
                    FragmentMaker.favoriteList(),
                )
                true
            }

            R.id.scan_qr_code -> {
                Navigator.showScreen(
                    activity as AppCompatActivity,
                    FragmentMaker.scan(),
                )
                true
            }

            R.id.credits -> {
                Navigator.showScreen(
                    activity as AppCompatActivity,
                    FragmentMaker.credits(),
                )
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        platformCategory = Platform.Category.valueOf(
            arguments?.getString(FragmentMaker.KEY_PLATFORM_CATEGORY)!!,
        )
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.title = platformCategory.displayName
        }
        adapter = PlatformListAdapter(activity as Activity)
        view.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PlatformListFragment.adapter
        }
        setRecycler()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecycler() {
        val items = Platform.values()
            .filter {
                it.category == platformCategory
            }
            .map {
                PlatformItem(it)
            }
        adapter.itemList.clear()
        adapter.itemList.addAll(items)
        adapter.notifyDataSetChanged()
    }
}
