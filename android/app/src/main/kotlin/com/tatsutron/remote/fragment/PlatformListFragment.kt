package com.tatsutron.remote.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.R
import com.tatsutron.remote.model.Platform
import com.tatsutron.remote.recycler.PlatformItem
import com.tatsutron.remote.recycler.PlatformListAdapter
import com.tatsutron.remote.util.FragmentMaker

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
        inflater.inflate(R.menu.menu_empty, menu)
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

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view?.findViewById(R.id.toolbar))
            supportActionBar?.title = platformCategory.name
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        platformCategory = Platform.Category.valueOf(
            arguments?.getString(FragmentMaker.KEY_PLATFORM_CATEGORY)!!,
        )
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
