package com.dat.swipe_example.swipe_fragment.base_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.dat.swipe_layout.swipe.SwipeLayout
import com.dat.swipe_example.swipe_fragment.NavigationManager
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeListener
import com.dat.swipe_layout.model.SwipeDirection


abstract class BaseSwipeableFragment<T : ViewBinding>(bindingInflater: (layoutInflater: LayoutInflater) -> T) :
    BaseFragment<T>(bindingInflater), SwipeListener {
    private var rootFrameLayout: FrameLayout? = null
    private var contentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentView = super.onCreateView(inflater, container, savedInstanceState)
        rootFrameLayout = SwipeLayout(requireContext(), contentView!!, getSwipeConfig()).also {
            it.removeAllViews()
            it.addView(contentView)
        }
        return rootFrameLayout
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        rootFrameLayout?.removeAllViews()
        rootFrameLayout = null
        contentView = null
        super.onDestroyView()
    }

    protected open fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .swipeDirection(SwipeDirection.LEFT_TO_RIGHT)
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

