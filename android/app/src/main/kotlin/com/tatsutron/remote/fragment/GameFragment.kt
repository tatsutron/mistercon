package com.tatsutron.remote.fragment

import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tatsutron.remote.*
import com.tatsutron.remote.model.Game
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.play -> {
                val context = requireContext()
                Dialog.confirm(
                    context = context,
                    message = context.getString(
                        R.string.confirm_play_game,
                        game.release?.releaseTitleName
                            ?: File(game.path).nameWithoutExtension,
                    ),
                    ok = {
                        game.play(requireActivity())
                    },
                )
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
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.game_toolbar))
            supportActionBar?.title = game.release?.releaseTitleName
                ?: File(game.path).nameWithoutExtension
        }
        if (game.sha1 == null) {
            view.findViewById<ProgressBar>(R.id.progress_bar)
                .visibility = View.VISIBLE
            Coroutine.launch(
                activity = requireActivity(),
                run = {
                    val session = Ssh.session()
                    Asset.put(requireContext(), session, "hash")
                    val command = StringBuilder().apply {
                        append("\"${Constants.HASH_PATH}\"")
                        append(" ")
                        append("\"${game.path}\"")
                        append(" ")
                        append(game.core.headerSizeInBytes.toString())
                    }.toString()
                    val output = Ssh.command(session, command)
                    Persistence.saveGame(
                        core = game.core.name,
                        path = game.path,
                        hash = output.trim(),
                    )
                    session.disconnect()
                },
                success = {
                    view.findViewById<ProgressBar>(R.id.progress_bar)
                        .visibility = View.GONE
                    game = Persistence.getGameByPath(game.path)!!
                    populate(view)
                },
                failure = {
                    view.findViewById<ProgressBar>(R.id.progress_bar)
                        .visibility = View.GONE
                }
            )
        } else {
            populate(view)
        }
    }

    private fun populate(view: View) {
        game.release?.releasePublisher?.let {
            view.findViewById<TextInputEditText>(R.id.publisher_text)
                .setText(it)
            view.findViewById<TextInputLayout>(R.id.publisher_layout)
                .visibility = View.VISIBLE
        }
        game.release?.releaseDeveloper?.let {
            view.findViewById<TextInputEditText>(R.id.developer_text)
                .setText(it)
            view.findViewById<TextInputLayout>(R.id.developer_layout)
                .visibility = View.VISIBLE
        }
        game.release?.releaseDate?.let {
            view.findViewById<TextInputEditText>(R.id.release_date_text)
                .setText(it)
            view.findViewById<TextInputLayout>(R.id.release_date_layout)
                .visibility = View.VISIBLE
        }
        game.region?.regionName?.let {
            view.findViewById<TextInputEditText>(R.id.region_text)
                .setText(it)
            view.findViewById<TextInputLayout>(R.id.region_layout)
                .visibility = View.VISIBLE
        }
        game.release?.releaseGenre?.let {
            view.findViewById<TextInputEditText>(R.id.genre_text)
                .setText(it)
            view.findViewById<TextInputLayout>(R.id.genre_layout)
                .visibility = View.VISIBLE
        }
        game.release?.releaseDescription?.let {
            view.findViewById<TextInputEditText>(R.id.description_text)
                .setText(it)
            view.findViewById<TextInputLayout>(R.id.description_layout)
                .visibility = View.VISIBLE
        }
        game.release?.releaseCoverFront?.let { url ->
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
        game.release?.releaseCoverBack?.let { url ->
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
        game.release?.releaseCoverCart?.let { url ->
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
        listOfNotNull(
            game.release?.releasePublisher,
            game.release?.releaseDeveloper,
            game.release?.releaseDate,
            game.region?.regionName,
            game.release?.releaseGenre,
            game.release?.releaseDescription,
            game.release?.releaseCoverFront,
            game.release?.releaseCoverBack,
            game.release?.releaseCoverCart,
        ).isEmpty().let { noData ->
            if (noData) {
                view.findViewById<ScrollView>(R.id.scroll)
                    .visibility = View.GONE
                view.findViewById<TextView>(R.id.no_data_text).apply {
                    text = context.getString(
                        R.string.no_data_was_found_for_game,
                        File(game.path).nameWithoutExtension,
                    )
                    visibility = View.VISIBLE
                }
            }
        }
    }
}
