package com.tatsutron.remote.fragment

import android.lib.widget.verticalmarqueetextview.VerticalMarqueeTextView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tatsutron.remote.R
import com.tatsutron.remote.util.User

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
                addAll(
                    listOf(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "Patrons",
                        "",
                        "\uD83D\uDC99 Aaron Thompson \uD83D\uDC99",
                        "\uD83D\uDC99 Arufonsu \uD83D\uDC99",
                        "\uD83D\uDC99 Christopher Gelatt \uD83D\uDC99",
                        "\uD83D\uDC99 Clinton Bobrowski \uD83D\uDC99",
                        "\uD83D\uDC99 Dandi and Matija \uD83D\uDC99",
                        "\uD83D\uDC99 Daniel Lugo \uD83D\uDC99",
                        "\uD83D\uDC99 Defkyle \uD83D\uDC99",
                        "\uD83D\uDC99 DoOoM \uD83D\uDC99",
                        "\uD83D\uDC99 Edward Hartley \uD83D\uDC99",
                        "\uD83D\uDC99 Edward Mallett \uD83D\uDC99",
                        "\uD83D\uDC99 Filip Kindt \uD83D\uDC99",
                        "\uD83D\uDC99 GeorgZ \uD83D\uDC99",
                        "\uD83D\uDC99 Greg Dyke \uD83D\uDC99",
                        "\uD83D\uDC99 Gryzor/CPCwiki \uD83D\uDC99",
                        "\uD83D\uDC99 His Royal Majesty Norton VI., Dei Gratia, Emporer of the United States and Protector of Mexico \uD83D\uDC99",
                        "\uD83D\uDC99 Jason Carps \uD83D\uDC99",
                        "\uD83D\uDC99 Jawler \uD83D\uDC99",
                        "\uD83D\uDC99 Jeremy Hopkins \uD83D\uDC99",
                        "\uD83D\uDC99 Johan \uD83D\uDC99",
                        "\uD83D\uDC99 Keith Gordon \uD83D\uDC99",
                        "\uD83D\uDC99 KremlingKuthroat19 \uD83D\uDC99",
                        "\uD83D\uDC99 Lakhdar Omar \uD83D\uDC99",
                        "\uD83D\uDC99 Levi Prinzing \uD83D\uDC99",
                        "\uD83D\uDC99 Louis Martinez \uD83D\uDC99",
                        "\uD83D\uDC99 Marc SE \uD83D\uDC99",
                        "\uD83D\uDC99 Markus Kraus \uD83D\uDC99",
                        "\uD83D\uDC99 Matt Keaveney \uD83D\uDC99",
                        "\uD83D\uDC99 Maxwell \uD83D\uDC99",
                        "\uD83D\uDC99 Mist Sonata \uD83D\uDC99",
                        "\uD83D\uDC99 Mitchell Ogden \uD83D\uDC99",
                        "\uD83D\uDC99 Nik Outchcunis \uD83D\uDC99",
                        "\uD83D\uDC99 Nolan Mars \uD83D\uDC99",
                        "\uD83D\uDC99 Onno Feringa \uD83D\uDC99",
                        "\uD83D\uDC99 Patrik Rosenhall \uD83D\uDC99",
                        "\uD83D\uDC99 peanutmans \uD83D\uDC99",
                        "\uD83D\uDC99 Rocco \uD83D\uDC99",
                        "\uD83D\uDC99 Sean \uD83D\uDC99",
                        "\uD83D\uDC99 Sergio L. Serrano \uD83D\uDC99",
                        "\uD83D\uDC99 Tim Lehner \uD83D\uDC99",
                        "\uD83D\uDC99 Tom B \uD83D\uDC99",
                        "\uD83D\uDC99 Weasel5053 \uD83D\uDC99",
                        "\uD83D\uDC99 WhiteScreen \uD83D\uDC99",
                        "\uD83D\uDC99 Whittier H \uD83D\uDC99",
                        "",
                        "",
                        "Special Thanks",
                        "",
                        "\uD83E\uDDE1 mrsonicblue \uD83E\uDDE1",
                        "\uD83E\uDDE1 OpenVGDB \uD83E\uDDE1",
                        "\uD83E\uDDE1 pocomane \uD83E\uDDE1",
                        "\uD83E\uDDE1 wizzomafizzo \uD83E\uDDE1",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "Love,",
                        "Sio & Casey",
                        "\uD83D\uDC9A\uD83D\uDC96",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                    )
                )
            }.joinToString("\n")
        }
        view.findViewById<TextView>(R.id.postscript).apply {
            text = if (User.isPatron) {
                context.getText(R.string.thank_you)
            } else {
                context.getText(R.string.subscribe)
            }
        }
        view.findViewById<TextView>(R.id.version_name).apply {
            val versionName = context.packageManager
                .getPackageInfo(context.packageName, 0)
                .versionName
            val version = "$versionName${if (User.isPatron) "p" else ""}"
            text = version
        }
    }
}
