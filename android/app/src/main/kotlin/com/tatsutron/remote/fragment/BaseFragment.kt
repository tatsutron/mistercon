package com.tatsutron.remote.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    open fun onBackStackChanged() {}
}
