package com.dat.swipe_layout.swipe.view_drag_callback

import android.util.Log
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.dat.swipe_layout.model.ViewDragHelperParams
import com.dat.swipe_layout.swipe.SwipeLayout
import kotlin.math.abs

abstract class BaseViewDragHelperCallback(
    params: ViewDragHelperParams
) : ViewDragHelper.Callback() {

    protected var config = params.config
    protected var decorView = params.decorView
    protected var screenWidth = params.screenWidth
    protected var screenHeight = params.screenHeight
    protected var viewDragHelperListener = params.viewDragHelperListener
    var startSwipeTime = 0L

    fun clamp(value: Int, min: Int, max: Int): Int {
        return min.coerceAtLeast(max.coerceAtMost(value))
    }

    fun getSettle(
        mainAxisPosition: Int,
        mainAxisVel: Float,
        crossAxisVel: Float,
        dismissPosition: Int,
        is2Direction: Boolean = false
    ): Int {
        val isCrossSwiping = abs(crossAxisVel) > config.velocityThreshold
        val isMainSwiping = abs(mainAxisVel) > config.velocityThreshold
        val threshold = (abs(dismissPosition) * config.distanceThreshold).toInt()
        if ((isMainSwiping && !isCrossSwiping) || abs(mainAxisPosition) > threshold || checkIsQuickDismiss()) {
            return if (is2Direction && mainAxisPosition < 0)
                -dismissPosition
            else
                dismissPosition
        }
        return 0
    }

    fun updateDragStateChange(state: Int, vararg position: Int?) {
        viewDragHelperListener.onDragStateChanged(state, *position)

    }

    fun checkIsQuickDismiss(): Boolean {
        val timeQuickDismiss = config.timeQuickDismiss ?: return false
        val currentTime = System.currentTimeMillis()
        val start = startSwipeTime
        // check quick dismiss
        if (currentTime - start < timeQuickDismiss) {
            Log.d(SwipeLayout.TAG, " quickDismiss")
            return true
        }
        return false
    }

    fun checkDisMissRightAway(vararg default: Int): Boolean {
        if (default.any { it != 0 } && config.isDismissRightAway)
            return true
        return false
    }

    fun getClampTop() = if (!canChildScrollDown()) -screenHeight else 0
    fun getClamBottom() = if (!canChildScrollUp()) screenHeight else 0
    fun getClampLeft() = if (!canChildScrollRight()) -screenWidth else 0
    fun getClamRight() = if (!canChildScrollLeft()) screenHeight else 0

    fun canChildScrollUp(): Boolean {
        return ViewCompat.canScrollVertically(decorView, -1)
    }

    fun canChildScrollDown(): Boolean {
        return ViewCompat.canScrollVertically(decorView, 1)
    }

    fun canChildScrollLeft(): Boolean {
        return ViewCompat.canScrollHorizontally(decorView, -1)
    }

    fun canChildScrollRight(): Boolean {
        return ViewCompat.canScrollHorizontally(decorView, 1)
    }
}
