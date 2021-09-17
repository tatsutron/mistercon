package com.tatsutron.remote

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.tatsutron.remote.fragment.MainFragment
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Persistence

class MainActivity : AppCompatActivity() {

    private lateinit var loadingScreen: RelativeLayout

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
    }

    private fun showLoadingScreen() {
        loadingScreen.visibility = View.VISIBLE
    }

    private fun hideLoadingScreen() {
        loadingScreen.visibility = View.GONE
    }
}
