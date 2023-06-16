package com.tatsutron.remote.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.jcraft.jsch.JSchException
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.tatsutron.remote.R
import com.tatsutron.remote.component.ImageCard
import com.tatsutron.remote.component.MetadataCard
import com.tatsutron.remote.model.Game
import com.tatsutron.remote.model.Metadata
import com.tatsutron.remote.util.Coroutine
import com.tatsutron.remote.util.Dialog
import com.tatsutron.remote.util.FragmentMaker
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Persistence
import com.tatsutron.remote.util.Util
import com.tatsutron.remote.util.getColorCompat

class GameFragment : BaseFragment() {

    private lateinit var game: Game
    private var metadata: Metadata? = null
    private lateinit var playAction: SpeedDialActionItem
    private lateinit var favoriteAction: SpeedDialActionItem
    private lateinit var unfavoriteAction: SpeedDialActionItem
    private lateinit var syncAction: SpeedDialActionItem
    private lateinit var copyQrAction: SpeedDialActionItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_play, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.play -> {
                onPlay()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        game = Persistence.getGameByPath(
            arguments?.getString(FragmentMaker.KEY_PATH)!!
        )!!
        if (game.sha1 != null) {
            metadata = Persistence.getMetadataBySha1(game.sha1!!)
        }
        val toolbar = view.findViewById<Toolbar>(R.id.game_toolbar)
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.title = game.name
        }
        setSpeedDialActionItems()
        setSpeedDial()
        if (!game.platform.metadata) {
            setText(requireContext().getString(R.string.metadata_not_supported))
        } else if (game.sha1 == null) {
            onSync()
        } else {
            setMetadata()
        }
    }

    private fun setSpeedDialActionItems() {
        val context = requireContext()
        playAction = SpeedDialActionItem.Builder(R.id.play, R.drawable.ic_play)
            .setLabel(context.getString(R.string.play))
            .setLabelBackgroundColor(context.getColorCompat(R.color.button_background))
            .setLabelColor(context.getColorCompat(R.color.button_label))
            .setFabBackgroundColor(context.getColorCompat(R.color.button_background))
            .setFabImageTintColor(context.getColorCompat(R.color.button_label))
            .create()
        favoriteAction = SpeedDialActionItem.Builder(R.id.favorite, R.drawable.ic_star_outline)
            .setLabel(context.getString(R.string.favorite))
            .setLabelBackgroundColor(context.getColorCompat(R.color.button_background))
            .setLabelColor(context.getColorCompat(R.color.button_label))
            .setFabBackgroundColor(context.getColorCompat(R.color.button_background))
            .setFabImageTintColor(context.getColorCompat(R.color.button_label))
            .create()
        unfavoriteAction = SpeedDialActionItem.Builder(R.id.unfavorite, R.drawable.ic_star_fill)
            .setLabel(context.getString(R.string.unfavorite))
            .setLabelBackgroundColor(context.getColorCompat(R.color.button_background))
            .setLabelColor(context.getColorCompat(R.color.button_label))
            .setFabBackgroundColor(context.getColorCompat(R.color.button_background))
            .setFabImageTintColor(context.getColorCompat(R.color.button_label))
            .create()
        syncAction = SpeedDialActionItem.Builder(R.id.sync, R.drawable.ic_sync)
            .setLabel(context.getString(R.string.sync))
            .setLabelBackgroundColor(context.getColorCompat(R.color.button_background))
            .setLabelColor(context.getColorCompat(R.color.button_label))
            .setFabBackgroundColor(context.getColorCompat(R.color.button_background))
            .setFabImageTintColor(context.getColorCompat(R.color.button_label))
            .create()
        copyQrAction = SpeedDialActionItem.Builder(R.id.copy_qr, R.drawable.ic_copy)
            .setLabel(context.getString(R.string.copy_qr_data))
            .setLabelBackgroundColor(context.getColorCompat(R.color.button_background))
            .setLabelColor(context.getColorCompat(R.color.button_label))
            .setFabBackgroundColor(context.getColorCompat(R.color.button_background))
            .setFabImageTintColor(context.getColorCompat(R.color.button_label))
            .create()
    }

    private fun setSpeedDial() {
        view?.findViewById<SpeedDialView>(R.id.speed_dial)?.apply {
            clearActionItems()
            addActionItem(playAction)
            if (game.favorite) {
                addActionItem(unfavoriteAction)
            } else {
                addActionItem(favoriteAction)
            }
            if (game.platform.metadata) {
                addActionItem(syncAction)
            }
            if (game.sha1 != null) {
                addActionItem(copyQrAction)
            }
            setOnActionSelectedListener(
                SpeedDialView.OnActionSelectedListener { actionItem ->
                    when (actionItem.id) {
                        R.id.copy_qr -> {
                            onCopyQr()
                            close()
                            return@OnActionSelectedListener true
                        }
                        R.id.favorite -> {
                            onToggleFavorite()
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
                        R.id.unfavorite -> {
                            onToggleFavorite()
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
        metadata?.publisher?.let {
            if (it.isNotBlank()) {
                view?.findViewById<MetadataCard>(R.id.publisher)?.set(it)
            }
        }
        metadata?.developer?.let {
            if (it.isNotBlank()) {
                view?.findViewById<MetadataCard>(R.id.developer)?.set(it)
            }
        }
        metadata?.releaseDate?.let {
            if (it.isNotBlank()) {
                view?.findViewById<MetadataCard>(R.id.release_date)
                    ?.set(it)
            }
        }
        metadata?.region?.let {
            if (it.isNotBlank()) {
                view?.findViewById<MetadataCard>(R.id.region)?.set(it)
            }
        }
        metadata?.genre?.let {
            if (it.isNotBlank()) {
                view?.findViewById<MetadataCard>(R.id.genre)?.set(it)
            }
        }
        metadata?.description?.let {
            if (it.isNotBlank()) {
                view?.findViewById<MetadataCard>(R.id.description)?.set(it)
            }
        }
        metadata?.frontCover?.let { url ->
            if (url.isNotBlank()) {
                view?.findViewById<ImageCard>(R.id.front_cover)
                    ?.set(requireActivity(), url)
            }
        }
        metadata?.backCover?.let { url ->
            if (url.isNotBlank()) {
                view?.findViewById<ImageCard>(R.id.back_cover)
                    ?.set(requireActivity(), url)
            }
        }
        metadata?.cartridge?.let { url ->
            if (url.isNotBlank()) {
                view?.findViewById<ImageCard>(R.id.cartridge)
                    ?.set(requireActivity(), url)
            }
        }
        if (
            listOf(
                metadata?.publisher?.isNotBlank(),
                metadata?.developer?.isNotBlank(),
                metadata?.releaseDate?.isNotBlank(),
                metadata?.region?.isNotBlank(),
                metadata?.genre?.isNotBlank(),
                metadata?.description?.isNotBlank(),
                metadata?.frontCover?.isNotBlank(),
                metadata?.backCover?.isNotBlank(),
                metadata?.cartridge?.isNotBlank(),
            ).none {
                it == true
            }
        ) {
            setText(
                requireContext().getString(
                    R.string.no_data_was_found_for_game,
                    game.name,
                )
            )
        }
    }

    private fun setText(text: String) {
        view?.findViewById<ScrollView>(R.id.scroll)
            ?.visibility = View.GONE
        view?.findViewById<TextView>(R.id.no_data_text)?.apply {
            this.text = text
            visibility = View.VISIBLE
        }
    }

    private fun onPlay() {
        Navigator.showLoadingScreen()
        Util.loadGame(
            activity = requireActivity(),
            game = game,
            callback = {
                Navigator.hideLoadingScreen()
            },
        )
    }

    private fun onToggleFavorite() {
        Persistence.favoriteGame(game, !game.favorite)
        game = Persistence.getGameByPath(game.path)!!
        setSpeedDial()
    }

    private fun onSync() {
        Navigator.showLoadingScreen()
        val activity = requireActivity()
        Coroutine.launch(
            activity = activity,
            run = {
                val sha1 = Util.hash(
                    path = game.path,
                    headerSizeInBytes = game.platform.headerSizeInBytes ?: 0,
                )
                Persistence.saveGame(
                    path = game.path,
                    platform = game.platform,
                    sha1 = sha1,
                )
            },
            success = {
                game = Persistence.getGameByPath(game.path)!!
                metadata = Persistence.getMetadataBySha1(game.sha1!!)
                setSpeedDial()
                setMetadata()
            },
            failure = { throwable ->
                when (throwable) {
                    is JSchException ->
                        Dialog.connectionFailed(
                            context = activity,
                            ipAddressSet = ::onSync,
                        )
                    else ->
                        Dialog.error(activity, throwable)
                }
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
