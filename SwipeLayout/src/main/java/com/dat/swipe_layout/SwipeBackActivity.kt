package com.dat.swipe_layout

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeListener
import com.dat.swipe_layout.swipe.SwipeLayout

/**
 * SwipeBackActivity will wrap your root view by SwipeLayout by default
 * and set config by default
*/
abstract class SwipeBackActivity : AppCompatActivity(), SwipeListener {
    var swipeLayout: SwipeLayout? = null
    override fun setContentView(view: View) {
//        SwipeUtils.convertActivityToTranslucent(this)
        swipeLayout = SwipeLayout(this, view, getSwipeConfig()).also {
            it.addView(view)
        }
        super.setContentView(swipeLayout!!.rootView)
    }
    open fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .build()
    }

    override fun onSwipeStateChanged(state: Int) {

    }
    override fun onSwipeChange(percent: Float) {

    }
    override fun onSwipeOpened() {

    }
    override fun onSwipeClosed() {
        finish()
    }
}