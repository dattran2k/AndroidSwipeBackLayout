package com.dat.swipe_layout.swipe.view_drag_callback

import android.util.Log
import android.view.View
import com.dat.swipe_layout.model.ViewDragHelperParams
import com.dat.swipe_layout.swipe.SwipeLayout
import kotlin.math.abs
/**
* The drag helper callbacks for dragging the swiper attachment from the top of the screen
*/
class LeftToRightFacebookCallback(
    params: ViewDragHelperParams
) : BaseViewDragHelperCallback(params) {
    private var isEnableScrollRight = false
    override fun tryCaptureView(child: View, pointerId: Int): Boolean {
        return child.id == decorView?.id &&viewDragHelperListener.isEdgeCase()
    }

    override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
        return if (isEnableScrollRight)
            clamp(left, getClampLeft() / 2, getClamRight())
        else
            clamp(left, 0, getClamRight())
    }

    override fun getViewHorizontalDragRange(child: View): Int {
        return screenWidth
    }

    override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
        super.onViewReleased(releasedChild, xvel, yvel)
        var settleLeft = getSettle(
            releasedChild.left, xvel, yvel, screenWidth, true
        )
        if (settleLeft < 0)
            settleLeft = 0
        if (checkDisMissRightAway(settleLeft))
            return
        isEnableScrollRight = false
        viewDragHelperListener.onViewReleased(settleLeft, releasedChild.top)
    }

    override fun onViewPositionChanged(
        changedView: View, left: Int, top: Int, dx: Int, dy: Int
    ) {
        super.onViewPositionChanged(changedView, left, top, dx, dy)
        if (startSwipeTime == 0L) startSwipeTime = System.currentTimeMillis()
        val percent = 1f - abs(left).toFloat() / screenWidth.toFloat()
        if (left > 0) isEnableScrollRight = true
        viewDragHelperListener.onSwipeChange(percent)
    }

    override fun onViewDragStateChanged(state: Int) {
        super.onViewDragStateChanged(state)
        Log.e(SwipeLayout.TAG, "onViewDragStateChanged: $state + ${decorView.left}")
        updateDragStateChange(state, decorView.left)
    }
}