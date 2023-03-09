package com.dat.swipe_layout

import android.view.View
import androidx.fragment.app.Fragment
import com.dat.swipe_layout.model.SwipeDirection
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeListener
import com.dat.swipe_layout.swipe.SwipeLayout

abstract class SwipeBackFragment : Fragment(), SwipeListener {
    var swipeLayout: SwipeLayout? = null
    fun wrapSwipeLayout(view: View) : SwipeLayout{
        val layout = SwipeLayout(requireContext(), view, getSwipeConfig()).apply {
            addView(view)
        }
        swipeLayout = layout
        return layout
    }

    open fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .swipeDirection(SwipeDirection.LEFT_TO_RIGHT)
            .build()
    }

    override fun onSwipeStateChanged(state: Int) {

    }

    override fun onSwipeChange(percent: Float) {

    }

    override fun onSwipeOpened() {

    }

    override fun onSwipeClosed() {
        activity?.onBackPressed()
    }
}