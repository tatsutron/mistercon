package com.tatsutron.remote.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.tatsutron.remote.MainActivity
import com.tatsutron.remote.R

object Navigator {

    private var activity: MainActivity? = null

    fun init(activity: MainActivity) {
        Navigator.activity = activity
    }

    fun show(fragment: Fragment) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.setTransition(FragmentTransaction.TRANSIT_NONE)
            ?.add(R.id.root, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }
}
