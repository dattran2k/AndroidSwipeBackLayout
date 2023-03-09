package com.dat.swipe_example.swipe_fragment.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.dat.swipe_example.databinding.FragmentFirstBinding
import com.dat.swipe_example.swipe_fragment.NavigationManager
import com.dat.swipe_example.swipe_fragment.base_fragment.BaseFragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment<FragmentFirstBinding>(FragmentFirstBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.im.setOnClickListener {
            NavigationManager.getInstance().openFragment(SecondFragment())
        }
    }

}