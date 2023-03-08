package com.dat.swipe_layout.swipe.view_drag_callback

import android.util.Log
import android.view.View
import com.dat.swipe_layout.model.ViewDragHelperParams
import com.dat.swipe_layout.swipe.SwipeLayout
import kotlin.math.abs
/**
 * The drag helper callback interface for the all
 */

class FreeCallbackCallback(
    params: ViewDragHelperParams
) : BaseViewDragHelperCallback(params) {

    override fun tryCaptureView(child: View, pointerId: Int): Boolean {
        return child.id == decorView.id && viewDragHelperListener.isEdgeCase()
    }

    override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
        return clamp(left, getClampLeft(), getClamRight())
    }

    override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
        return clamp(top, getClampTop(), getClamBottom())
    }

    override fun getViewHorizontalDragRange(child: View): Int {
        return screenWidth
    }

    override fun getViewVerticalDragRange(child: View): Int {
        return screenHeight
    }

    override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
        super.onViewReleased(releasedChild, xvel, yvel)
        val settleTop = getSettle(
            releasedChild.top, yvel, xvel, screenHeight, true
        )
        val settleLeft = getSettle(
            releasedChild.left, xvel, yvel, screenWidth, true
        )
//            val settleLeft = if (settleTop != 0) releasedChild.left else 0
        // check dismiss rightAway, ignore scroll to top or bottom
        // isDismissRightAway always true on this
        if (checkDisMissRightAway(settleTop, settleLeft))
            return
        viewDragHelperListener.onViewReleased(settleLeft, settleTop)

    }

    override fun onViewPositionChanged(
        changedView: View, left: Int, top: Int, dx: Int, dy: Int
    ) {
        super.onViewPositionChanged(changedView, left, top, dx, dy)
        if (startSwipeTime == 0L) startSwipeTime = System.currentTimeMillis()
        val percentHeight = 1f - abs(top).toFloat() / screenHeight.toFloat()
        val percentWidth = 1f - abs(left).toFloat() / screenWidth.toFloat()
        val percent = (percentHeight + percentWidth) / 2f
        viewDragHelperListener.onSwipeChange(percent)
    }

    override fun onViewDragStateChanged(state: Int) {
        super.onViewDragStateChanged(state)
        // TODO dragstate jump to 0, check
        Log.e(SwipeLayout.TAG, "onViewDragStateChanged: $state + ${decorView.left} + ${decorView.top}")
        updateDragStateChange(state, decorView.left, decorView.top)
    }
}