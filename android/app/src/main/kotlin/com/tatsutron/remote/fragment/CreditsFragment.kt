package com.tatsutron.remote.fragment

import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tatsutron.remote.R
import com.tatsutron.remote.util.Persistence

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
                            "Aaron Thompson",
                            "Arufonsu",
                            "Christopher Gelatt",
                            "Clinton Bobrowski",
                            "Dandi and Matija",
                            "Daniel Lugo",
                            "Defkyle",
                            "DoOoM",
                            "Edward Hartley",
                            "Edward Mallett",
                            "Filip Kindt",
                            "GeorgZ",
                            "Greg Dyke",
                            "Gryzor/CPCwiki",
                            "His Royal Majesty Norton VI., Dei Gratia, Emporer of the United States and Protector of Mexico",
                            "Jason Carps",
                            "Jawler",
                            "Jeremy Hopkins",
                            "Johan",
                            "Keith Gordon",
                            "KremlingKuthroat19",
                            "Lakhdar Omar",
                            "Levi Prinzing",
                            "Louis Martinez",
                            "Marc SE",
                            "Markus Kraus",
                            "Matt Keaveney",
                            "Maxwell",
                            "Mist Sonata",
                            "Mitchell Ogden",
                            "Nik Outchcunis",
                            "Nolan Mars",
                            "Onno Feringa",
                            "Patrik Rosenhall",
                            "peanutmans",
                            "Rocco",
                            "Sean",
                            "Sergio L. Serrano",
                            "Tim Lehner",
                            "Tom B",
                            "Weasel5053",
                            "WhiteScreen",
                            "Whittier H",
                        )
                    )
                }
            }.joinToString("\n")
        }
        view.findViewById<TextView>(R.id.postscript).apply {
            text = if (Persistence.isPatron) {
                context.getText(R.string.thank_you)
            } else {
                context.getText(R.string.subscribe)
            }
        }
        view.findViewById<TextView>(R.id.version_name).apply {
            text = context.packageManager
                .getPackageInfo(context.packageName, 0)
                .versionName
        }
    }
}
