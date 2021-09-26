package com.tatsutron.remote.fragment

import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        view.findViewById<VerticalMarqueeTextView>(R.id.credits_scroll).apply {
            text = mutableListOf<String>().apply {
                repeat(99) {
                    addAll(
                        listOf(
                            "Arufonsu",
                            "DoOoM",
                            "Edward Mallett",
                            "GeorgZ",
                            "Greg Dyke",
                            "Jeremy Hopkins",
                            "KremlingKuthroat19",
                            "Louis Martinez",
                            "Markus Kraus",
                            "Mist Sonata",
                            "peanutmans",
                            "Sergio L. Serrano",
                            "Tim Lehner",
                            "Tom B",
                            "Weasel5053",
                        )
                    )
                }
            }.joinToString("\n")
        }
    }
}
