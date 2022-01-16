package com.tatsutron.remote

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.tatsutron.remote.fragment.BaseFragment
import com.tatsutron.remote.fragment.MainFragment
import com.tatsutron.remote.util.Navigator
import com.tatsutron.remote.util.Persistence

class MainActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val loadingScreen = findViewById<RelativeLayout>(R.id.loading_screen)
        if (loadingScreen.visibility != View.VISIBLE) {
            val fragment = supportFragmentManager.fragments.last()
            if ((fragment as? BaseFragment)?.onBackPressed() != true) {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Persistence.init(baseContext)
        Navigator.init(findViewById(R.id.loading_screen))
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, MainFragment())
                .commit()
        }
    }
}
