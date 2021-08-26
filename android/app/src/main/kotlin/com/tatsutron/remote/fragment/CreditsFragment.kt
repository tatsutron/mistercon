package com.tatsutron.remote.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tatsutron.remote.R

class CreditsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_credits,
            container,
            false, // attachToRoot
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<LinearLayout>(R.id.credits_container).apply {
            listOf(
                "DoOoM",
                "KremlingKuthroat19",
                "Luke Earnshaw",
                "Mist Sonata",
            ).forEach {
                val item = layoutInflater.inflate(R.layout.item_credit, null)
                item.findViewById<TextView>(R.id.label).text = it
                addView(item)
            }
        }
    }
}
