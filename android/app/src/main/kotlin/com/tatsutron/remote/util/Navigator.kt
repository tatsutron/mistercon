package com.tatsutron.remote.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.tatsutron.remote.R

object Navigator {

    private lateinit var showLoadingScreen: () -> Unit
    private lateinit var hideLoadingScreen: () -> Unit

    fun init(showLoadingScreen: () -> Unit, hideLoadingScreen: () -> Unit) {
        this.showLoadingScreen = showLoadingScreen
        this.hideLoadingScreen = hideLoadingScreen
    }

    fun show(activity: AppCompatActivity, fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_NONE)
            .add(R.id.root, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun showLoadingScreen() {
        showLoadingScreen.invoke()
    }

    fun hideLoadingScreen() {
        hideLoadingScreen.invoke()
    }
}
