package com.tatsutron.remote

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

object Navigator {

    private var activity: MainActivity? = null

    fun init(activity: MainActivity) {
        this.activity = activity
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
