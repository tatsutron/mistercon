package com.tatsutron.remote.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.util.*
import java.io.File

class GameFragment : Fragment() {
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_game, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        game = Persistence.getGameByPath(
            arguments?.getString(FragmentMaker.KEY_PATH)!!
        )!!
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.game_toolbar))
            supportActionBar?.title = game.name
        }
        val refresh = {
            setSpeedDial(view)
            populate(view)
        }
        if (game.sha1 == null) {
            view.findViewById<ProgressBar>(R.id.progress_bar)
                .visibility = View.VISIBLE
            Coroutine.launch(
                activity = requireActivity(),
                run = {
                    val session = Ssh.session()
                    Assets.require(requireContext(), session, "hash")
                    val headerSizeInBytes = game.console.formats
                        .find {
                            it.extension == File(game.path).extension
                        }
                        ?.headerSizeInBytes!!
                    val command = StringBuilder().apply {
                        append("\"${Constants.HASH_PATH}\"")
                        append(" ")
                        append("\"${game.path}\"")
                        append(" ")
                        append(headerSizeInBytes.toString())
                    }.toString()
                    val output = Ssh.command(session, command)
                    Persistence.saveGame(
                        core = game.console.name,
                        path = game.path,
                        hash = output.trim(),
                    )
                    session.disconnect()
                },
                success = {
                    view.findViewById<ProgressBar>(R.id.progress_bar)
                        .visibility = View.GONE
                    game = Persistence.getGameByPath(game.path)!!
                    refresh()
                },
                failure = {
                    view.findViewById<ProgressBar>(R.id.progress_bar)
                        .visibility = View.GONE
                }
            )
        } else {
            refresh()
        }
    }

    private fun setSpeedDial(view: View) {
        val context = requireContext()
        val string = { id: Int ->
            context.getString(id)
        }
        val color = { id: Int ->
            ResourcesCompat.getColor(resources, id, context.theme)
        }
        view.findViewById<SpeedDialView>(R.id.speed_dial).apply {
            addActionItem(
                SpeedDialActionItem.Builder(R.id.play, R.drawable.ic_play)
                    .setLabel(string(R.string.play))
                    .setLabelBackgroundColor(color(R.color.gray_900))
                    .setLabelColor(color(R.color.primary_500))
                    .setFabBackgroundColor(color(R.color.gray_900))
                    .setFabImageTintColor(color(R.color.primary_500))
                    .create()
            )
            addActionItem(
                SpeedDialActionItem.Builder(R.id.copy_qr, R.drawable.ic_copy)
                    .setLabel(string(R.string.copy_qr_data))
                    .setLabelBackgroundColor(color(R.color.gray_900))
                    .setLabelColor(color(R.color.primary_500))
                    .setFabBackgroundColor(color(R.color.gray_900))
                    .setFabImageTintColor(color(R.color.primary_500))
                    .create()
            )
            setOnActionSelectedListener(
                SpeedDialView.OnActionSelectedListener { actionItem ->
                    when (actionItem.id) {
                        R.id.copy_qr -> {
                            val clipboard = getSystemService(context, ClipboardManager::class.java)
                            clipboard?.setPrimaryClip(ClipData.newPlainText("QR", game.sha1))
                            Toast.makeText(
                                requireActivity(),
                                "Copied QR Data to Clipboard",
                                Toast.LENGTH_SHORT,
                            ).show()
                            close()
                            return@OnActionSelectedListener true
                        }
                        R.id.play -> {
                            game.play(requireActivity())
                            close()
                            return@OnActionSelectedListener true
                        }
                    }
                    false
                }
            )
            visibility = View.VISIBLE
        }
    }

    private fun populate(view: View) {
        game.release?.releasePublisher?.let {
            if (it.isNotBlank()) {
                view.findViewById<TextInputEditText>(R.id.publisher_text)
                    .setText(it)
                view.findViewById<TextInputLayout>(R.id.publisher_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.release?.releaseDeveloper?.let {
            if (it.isNotBlank()) {
                view.findViewById<TextInputEditText>(R.id.developer_text)
                    .setText(it)
                view.findViewById<TextInputLayout>(R.id.developer_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.release?.releaseDate?.let {
            if (it.isNotBlank()) {
                view.findViewById<TextInputEditText>(R.id.release_date_text)
                    .setText(it)
                view.findViewById<TextInputLayout>(R.id.release_date_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.region?.regionName?.let {
            if (it.isNotBlank()) {
                view.findViewById<TextInputEditText>(R.id.region_text)
                    .setText(it)
                view.findViewById<TextInputLayout>(R.id.region_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.release?.releaseGenre?.let {
            if (it.isNotBlank()) {
                view.findViewById<TextInputEditText>(R.id.genre_text)
                    .setText(it)
                view.findViewById<TextInputLayout>(R.id.genre_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.release?.releaseDescription?.let {
            if (it.isNotBlank()) {
                view.findViewById<TextInputEditText>(R.id.description_text)
                    .setText(it)
                view.findViewById<TextInputLayout>(R.id.description_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.release?.releaseCoverFront?.let { url ->
            if (url.isNotBlank()) {
                view.findViewById<ImageView>(R.id.front_cover_image).apply {
                    Glide.with(this@GameFragment)
                        .load(Uri.parse(url))
                        .into(this)
                    setOnClickListener {
                        Navigator.show(FragmentMaker.image(url))
                    }
                }
                view.findViewById<LinearLayout>(R.id.front_cover_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.release?.releaseCoverBack?.let { url ->
            if (url.isNotBlank()) {
                view.findViewById<ImageView>(R.id.back_cover_image).apply {
                    Glide.with(this@GameFragment)
                        .load(Uri.parse(url))
                        .into(this)
                    setOnClickListener {
                        Navigator.show(FragmentMaker.image(url))
                    }
                }
                view.findViewById<LinearLayout>(R.id.back_cover_layout)
                    .visibility = View.VISIBLE
            }
        }
        game.release?.releaseCoverCart?.let { url ->
            if (url.isNotBlank()) {
                view.findViewById<ImageView>(R.id.cartridge_image).apply {
                    Glide.with(this@GameFragment)
                        .load(Uri.parse(url))
                        .into(this)
                    setOnClickListener {
                        Navigator.show(FragmentMaker.image(url))
                    }
                }
                view.findViewById<LinearLayout>(R.id.cartridge_layout)
                    .visibility = View.VISIBLE
            }
        }
        if (
            listOf(
                game.release?.releasePublisher?.isNotBlank(),
                game.release?.releaseDeveloper?.isNotBlank(),
                game.release?.releaseDate?.isNotBlank(),
                game.region?.regionName?.isNotBlank(),
                game.release?.releaseGenre?.isNotBlank(),
                game.release?.releaseDescription?.isNotBlank(),
                game.release?.releaseCoverFront?.isNotBlank(),
                game.release?.releaseCoverBack?.isNotBlank(),
                game.release?.releaseCoverCart?.isNotBlank(),
            ).none {
                it == true
            }
        ) {
            view.findViewById<ScrollView>(R.id.scroll)
                .visibility = View.GONE
            view.findViewById<TextView>(R.id.no_data_text).apply {
                text = context.getString(
                    R.string.no_data_was_found_for_game,
                    game.name,
                )
                visibility = View.VISIBLE
            }
        }
    }
}
