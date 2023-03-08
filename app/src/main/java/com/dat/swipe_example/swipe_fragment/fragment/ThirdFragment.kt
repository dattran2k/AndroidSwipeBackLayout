package com.dat.swipe_example.swipe_fragment.fragment

import android.os.Bundle
import android.view.View
import com.dat.swipe_example.databinding.FragmentThirdBinding
import com.dat.swipe_example.swipe_fragment.NavigationManager
import com.dat.swipe_example.swipe_fragment.base_fragment.BaseFragment
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeListener
import com.dat.swipe_layout.model.SwipeDirection

/**
 * check layout file
 */
class ThirdFragment : BaseFragment<FragmentThirdBinding>(FragmentThirdBinding::inflate),
    SwipeListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setLateConfig(getSwipeConfig())
    }
    fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .edgeSize(0.5f)
            .edge(false)
            .endScrimThreshHold(0.7f)
            .swipeDirection(SwipeDirection.FREE)
            .scrimStartAlpha(1f)
            .build()
    }

    override fun onSwipeStateChanged(state: Int) {}

    override fun onSwipeChange(percent: Float) {}

    override fun onSwipeOpened() {}

    override fun onSwipeClosed() {
        NavigationManager.getInstance().popBackStack()
    }

}