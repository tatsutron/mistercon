package com.tatsutron.remote

import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.tatsutron.remote.fragment.ConsoleFragment
import com.tatsutron.remote.fragment.MainFragment
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Persistence

class MainActivity : AppCompatActivity() {

    private lateinit var loadingScreen: RelativeLayout
    private val fadeIn = AlphaAnimation(0.0f, 1.0f).apply {
        duration = 500
    }
    private val fadeOut = AlphaAnimation(1.0f, 0.0f).apply {
        duration = 500
    }

    override fun onBackPressed() =
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingScreen = findViewById(R.id.loading_screen)
        Persistence.init(baseContext)
        Navigator.init(::showLoadingScreen, ::hideLoadingScreen)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, MainFragment())
                .commit()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            (supportFragmentManager.fragments.last() as? ConsoleFragment)?.onBackStackChanged()
        }
    }

    private fun showLoadingScreen() {
        loadingScreen.animation = fadeIn
        loadingScreen.visibility = View.VISIBLE
    }

    private fun hideLoadingScreen() {
        loadingScreen.animation = fadeOut
        loadingScreen.visibility = View.GONE
    }
}
