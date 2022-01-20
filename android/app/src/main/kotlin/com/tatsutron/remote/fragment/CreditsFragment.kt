package com.tatsutron.remote.fragment

import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tatsutron.remote.R

class CreditsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.fragment_credits,
            container,
            false,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<VerticalMarqueeTextView>(R.id.credits_scroll).apply {
            text = mutableListOf<String>().apply {
                repeat(8) {
                    addAll(
                        listOf(
                            "Arufonsu",
                            "Daniel Lugo",
                            "DoOoM",
                            "Edward Mallett",
                            "GeorgZ",
                            "Greg Dyke",
                            "Gryzor/CPCwiki",
                            "Jason Carps",
                            "Jeremy Hopkins",
                            "Keith Gordon",
                            "KremlingKuthroat19",
                            "Lakhdar Omar",
                            "Levi Prinzing",
                            "Louis Martinez",
                            "Marc SE",
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
        view.findViewById<TextView>(R.id.version_name).apply {
            text = context.packageManager
                .getPackageInfo(context.packageName, 0)
                .versionName
        }
    }
}
