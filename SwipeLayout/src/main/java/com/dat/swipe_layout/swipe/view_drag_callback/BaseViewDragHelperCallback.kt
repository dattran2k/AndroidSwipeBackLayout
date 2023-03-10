package com.dat.swipe_layout.swipe.view_drag_callback

import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.dat.swipe_layout.model.ViewDragHelperParams
import com.dat.swipe_layout.swipe.SwipeLayout
import kotlin.math.abs

abstract class BaseViewDragHelperCallback(
    params: ViewDragHelperParams
) : ViewDragHelper.Callback() {

    private var config = params.config
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

    override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
        super.onViewPositionChanged(changedView, left, top, dx, dy)
        if (startSwipeTime == 0L) startSwipeTime = System.currentTimeMillis()
    }

    private fun checkIsQuickDismiss(): Boolean {
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

    protected fun checkDisMissRightAway(vararg default: Int): Boolean {
        if (default.any { it != 0 } && config.isDismissRightAway)
            return true
        return false
    }

    protected fun getClampTop() = if (!canChildScrollDown()) -screenHeight else 0
    protected fun getClamBottom() = if (!canChildScrollUp()) screenHeight else 0
    protected fun getClampLeft() = if (!canChildScrollRight()) -screenWidth else 0
    protected fun getClamRight() = if (!canChildScrollLeft()) screenHeight else 0

    private fun canChildScrollUp(): Boolean {
        return ViewCompat.canScrollVertically(decorView, -1)
    }

    private fun canChildScrollDown(): Boolean {
        return ViewCompat.canScrollVertically(decorView, 1)
    }

    private fun canChildScrollLeft(): Boolean {
        return ViewCompat.canScrollHorizontally(decorView, -1)
    }

    private fun canChildScrollRight(): Boolean {
        return ViewCompat.canScrollHorizontally(decorView, 1)
    }
}
