package com.dat.swipe_example.swipe_fragment

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.dat.swipe_example.R

import com.dat.swipe_example.databinding.ActivitySwipeFragmentBinding
import com.dat.swipe_example.swipe_fragment.fragment.FirstFragment
import com.dat.swipe_layout.SwipeBackActivity

class SwipeFragmentActivity : SwipeBackActivity(), FragmentManager.OnBackStackChangedListener {
    private lateinit var binding: ActivitySwipeFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NavigationManager.getInstance().init(this, supportFragmentManager, R.id.fragment_container)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, FirstFragment(), "FirstFragment")
            .addToBackStack("GridFragment")
            .commit()
        supportFragmentManager.addOnBackStackChangedListener(this)
    }

    override fun onBackStackChanged() {
        if(isRoot())
            swipeLayout?.unlock()
        else
            swipeLayout?.lock()
        if(isBackStackEmpty())
            finish()
    }
    fun isRoot() = supportFragmentManager.backStackEntryCount <= 1
    fun isBackStackEmpty() = supportFragmentManager.backStackEntryCount == 0
}