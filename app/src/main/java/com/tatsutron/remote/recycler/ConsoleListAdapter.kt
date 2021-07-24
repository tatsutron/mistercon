package com.tatsutron.remote.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.tatsutron.remote.Core
import com.tatsutron.remote.FragmentMaker
import com.tatsutron.remote.MainActivity
import com.tatsutron.remote.R

class ConsoleListAdapter(
    private val activity: MainActivity,
    private val context: Context,
) : RecyclerView.Adapter<ConsoleHolder>() {

    private var itemList = Core.values()
        .sortedBy {
            it.displayName
        }
        .map {
            ConsoleItem(
                label = it.displayName,
                onClick = {
                    activity.supportFragmentManager
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_NONE)
                        .add(R.id.root, FragmentMaker.console(it.name))
                        .addToBackStack(null)
                        .commit()
                },
            )
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ConsoleHolder {
        val layoutInflater = LayoutInflater.from(context)
        val itemView = layoutInflater.inflate(
            R.layout.item_console,
            parent,
            false, // attachToRoot
        )
        return ConsoleHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ConsoleHolder, position: Int) {
        holder.bind(item = itemList[position])
    }

    override fun getItemCount() = itemList.size
}