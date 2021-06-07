package com.tatsutron.remote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onBackPressed() =
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Persistence.init(baseContext)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root, MainFragment())
            .commit()
    }
}
