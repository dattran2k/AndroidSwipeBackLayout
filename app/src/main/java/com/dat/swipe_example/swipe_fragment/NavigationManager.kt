package com.dat.swipe_example.swipe_fragment

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.dat.swipe_example.R

class NavigationManager {
    private lateinit var mFragmentManager: FragmentManager
    private var mContentId: Int? = null

    companion object {
        fun getInstance(): NavigationManager = NavigationManagerHolder.navigationManagerHolder
    }

    private object NavigationManagerHolder {
        val navigationManagerHolder = NavigationManager()
    }

    fun init(fragmentManager: FragmentManager, @IdRes contentId: Int) {
        mFragmentManager = fragmentManager
        mContentId = contentId
    }


    /**
     * flag mark the Navigation is created on Fragment class
     */
    fun popBackStack() {
        mFragmentManager.popBackStack()
    }


    fun openFragment(
        fragment: Fragment,
        @AnimRes enter: Int,
        @AnimRes exit: Int,
        @AnimRes popEnter: Int,
        @AnimRes popExit: Int, ) {
        try {

            mFragmentManager.commit {
                setReorderingAllowed(true)
                if (enter != 0 || exit != 0 || popEnter != 0 || popExit != 0) {
                    setCustomAnimations(enter, exit, popEnter, popExit)
                }
                mContentId?.let {
                    add(it, fragment, fragment::class.simpleName)
                }
                addToBackStack((2147483646.0 * Math.random()).toInt().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun openFragment(fragment: Fragment) {
        openFragment(
            fragment,
            R.anim.slide_in_left,
            R.anim.opacity_1_to_0,
            0,
            R.anim.slide_out_right,
        )
    }

}