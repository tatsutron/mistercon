package com.tatsutron.remote.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.*
import com.tatsutron.remote.component.ImageCard
import com.tatsutron.remote.component.MetadataCard
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.model.Metadata
import com.tatsutron.remote.util.*
import java.io.File

class GameFragment : BaseFragment() {

    private lateinit var game: Game
    private lateinit var metadata: Metadata

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
        if (game.sha1 != null) {
            metadata = Persistence.getMetadataBySha1(game.sha1!!)!!
        }
        val toolbar = view.findViewById<Toolbar>(R.id.game_toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.title = game.name
        }
        setSpeedDial()
        if (game.sha1 == null) {
            onSync()
        } else {
            setMetadata()
        }
    }

    private fun setSpeedDial() {
        val context = requireContext()
        val string = { id: Int ->
            context.getString(id)
        }
        val color = { id: Int ->
            ResourcesCompat.getColor(resources, id, context.theme)
        }
        view?.findViewById<SpeedDialView>(R.id.speed_dial)?.apply {
            clearActionItems()
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
                SpeedDialActionItem.Builder(R.id.sync, R.drawable.ic_sync)
                    .setLabel(string(R.string.sync))
                    .setLabelBackgroundColor(color(R.color.gray_900))
                    .setLabelColor(color(R.color.primary_500))
                    .setFabBackgroundColor(color(R.color.gray_900))
                    .setFabImageTintColor(color(R.color.primary_500))
                    .create()
            )
            if (game.sha1 != null) {
                addActionItem(
                    SpeedDialActionItem.Builder(
                        R.id.copy_qr,
                        R.drawable.ic_copy,
                    )
                        .setLabel(string(R.string.copy_qr_data))
                        .setLabelBackgroundColor(color(R.color.gray_900))
                        .setLabelColor(color(R.color.primary_500))
                        .setFabBackgroundColor(color(R.color.gray_900))
                        .setFabImageTintColor(color(R.color.primary_500))
                        .create()
                )
            }
            setOnActionSelectedListener(
                SpeedDialView.OnActionSelectedListener { actionItem ->
                    when (actionItem.id) {
                        R.id.copy_qr -> {
                            onCopyQr()
                            close()
                            return@OnActionSelectedListener true
                        }
                        R.id.play -> {
                            onPlay()
                            close()
                            return@OnActionSelectedListener true
                        }
                        R.id.sync -> {
                            onSync()
                            close()
                            return@OnActionSelectedListener true
                        }
                    }
                    false
                }
            )
        }
    }

    private fun setMetadata() {
        with(metadata) {
            publisher?.let {
                if (it.isNotBlank()) {
                    view?.findViewById<MetadataCard>(R.id.publisher)?.set(it)
                }
            }
            developer?.let {
                if (it.isNotBlank()) {
                    view?.findViewById<MetadataCard>(R.id.developer)?.set(it)
                }
            }
            releaseDate?.let {
                if (it.isNotBlank()) {
                    view?.findViewById<MetadataCard>(R.id.release_date)
                        ?.set(it)
                }
            }
            region?.let {
                if (it.isNotBlank()) {
                    view?.findViewById<MetadataCard>(R.id.region)?.set(it)
                }
            }
            genre?.let {
                if (it.isNotBlank()) {
                    view?.findViewById<MetadataCard>(R.id.genre)?.set(it)
                }
            }
            description?.let {
                if (it.isNotBlank()) {
                    view?.findViewById<MetadataCard>(R.id.description)?.set(it)
                }
            }
            frontCover?.let { url ->
                if (url.isNotBlank()) {
                    view?.findViewById<ImageCard>(R.id.front_cover)
                        ?.set(requireActivity(), url)
                }
            }
            backCover?.let { url ->
                if (url.isNotBlank()) {
                    view?.findViewById<ImageCard>(R.id.back_cover)
                        ?.set(requireActivity(), url)
                }
            }
            cartridge?.let { url ->
                if (url.isNotBlank()) {
                    view?.findViewById<ImageCard>(R.id.cartridge)
                        ?.set(requireActivity(), url)
                }
            }
            if (
                listOf(
                    publisher?.isNotBlank(),
                    developer?.isNotBlank(),
                    releaseDate?.isNotBlank(),
                    region?.isNotBlank(),
                    genre?.isNotBlank(),
                    description?.isNotBlank(),
                    frontCover?.isNotBlank(),
                    backCover?.isNotBlank(),
                    cartridge?.isNotBlank(),
                ).none {
                    it == true
                }
            ) {
                view?.findViewById<ScrollView>(R.id.scroll)
                    ?.visibility = View.GONE
                view?.findViewById<TextView>(R.id.no_data_text)?.apply {
                    text = context.getString(
                        R.string.no_data_was_found_for_game,
                        game.name,
                    )
                    visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onPlay() {
        Navigator.showLoadingScreen()
        Application.loadGame(
            activity = requireActivity(),
            game = game,
            callback = {
                Navigator.hideLoadingScreen()
            },
        )
    }

    private fun onSync() {
        Navigator.showLoadingScreen()
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
                game = Persistence.getGameByPath(game.path)!!
                metadata = Persistence.getMetadataBySha1(game.sha1!!)!!
                setSpeedDial()
                setMetadata()
            },
            finally = {
                Navigator.hideLoadingScreen()
            }
        )
    }

    private fun onCopyQr() {
        val context = requireContext()
        val clipboard = ContextCompat.getSystemService(
            context,
            ClipboardManager::class.java,
        )
        clipboard?.setPrimaryClip(ClipData.newPlainText("QR", game.sha1))
        Toast.makeText(
            requireActivity(),
            "Copied QR Data to Clipboard",
            Toast.LENGTH_SHORT,
        ).show()
    }
}
