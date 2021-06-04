package com.tatsutron.remote

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GameFragment : Fragment(), CoroutineScope by MainScope() {

    companion object {
        private const val KEY_FILENAME = "KEY_FILENAME"

        fun newInstance(filename: String): GameFragment {
            val fragment = GameFragment()
            val args = Bundle()
            args.putString(KEY_FILENAME, filename)
            fragment.arguments = args
            return fragment
        }
    }

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
                val filename = arguments?.getString(KEY_FILENAME)
                val game = Persistence.getGame(filename!!)!!
                launch(Dispatchers.IO) {
                    runCatching {
                        game.core.play(game.filename)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val filename = arguments?.getString(KEY_FILENAME)
        val game = Persistence.getGame(filename!!)!!
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(view.findViewById(R.id.game_toolbar))
            supportActionBar?.title = game.release?.releaseTitleName
                ?: game.filename
        }
        view.findViewById<TextCard>(R.id.publisher).set(
            game.release?.releasePublisher
        )
        view.findViewById<TextCard>(R.id.developer).set(
            game.release?.releaseDeveloper
        )
        view.findViewById<TextCard>(R.id.release_date).set(
            game.release?.releaseDate
        )
        view.findViewById<TextCard>(R.id.region).set(
            game.region?.regionName
        )
        view.findViewById<ImageCard>(R.id.front_cover).set(
            game.release?.releaseCoverFront
        )
        view.findViewById<ImageCard>(R.id.back_cover).set(
            game.release?.releaseCoverBack
        )
        view.findViewById<ImageCard>(R.id.cartridge).set(
            game.release?.releaseCoverCart
        )
        view.findViewById<TextCard>(R.id.genre).set(
            game.release?.releaseGenre
        )
        view.findViewById<TextCard>(R.id.description).set(
            game.release?.releaseDescription
        )
    }
}
