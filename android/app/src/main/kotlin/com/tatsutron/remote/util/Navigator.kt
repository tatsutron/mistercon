package com.tatsutron.remote.util

import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.tatsutron.remote.R

object Navigator {

    private lateinit var showLoadingScreenImpl: () -> Unit
    private lateinit var hideLoadingScreenImpl: () -> Unit
    private val fadeIn = AlphaAnimation(0.0f, 1.0f).apply {
        duration = 500
    }
    private val fadeOut = AlphaAnimation(1.0f, 0.0f).apply {
        duration = 500
    }

    fun init(loadingScreen: RelativeLayout) {
        showLoadingScreenImpl = {
            loadingScreen.animation = fadeIn
            loadingScreen.visibility = View.VISIBLE
        }
        hideLoadingScreenImpl = {
            loadingScreen.animation = fadeOut
            loadingScreen.visibility = View.GONE
        }
    }

    fun showScreen(activity: AppCompatActivity, fragment: Fragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_NONE)
            .add(R.id.root, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun showLoadingScreen() {
        showLoadingScreenImpl.invoke()
    }

    fun hideLoadingScreen() {
        hideLoadingScreenImpl.invoke()
    }
}
