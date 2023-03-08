package com.dat.swipe_layout.swipe.view_drag_callback

import android.view.View
import com.dat.swipe_layout.model.ViewDragHelperParams
import kotlin.math.abs
/**
 * The drag helper callbacks for dragging the swiper attachment in both vertical directions
 */
class VerticalCallback(
    params: ViewDragHelperParams
) : BaseViewDragHelperCallback(params) {

    override fun tryCaptureView(child: View, pointerId: Int): Boolean {
        return child.id == decorView.id && viewDragHelperListener.isEdgeCase()
    }

    override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
        return clamp(top, getClampTop(), getClamBottom())
    }

    override fun getViewVerticalDragRange(child: View): Int {
        return screenHeight
    }

    override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
        super.onViewReleased(releasedChild, xvel, yvel)

        val settleTop = getSettle(
            releasedChild.top, if(yvel < 0) 0f else yvel , xvel, screenHeight, true
        )
        if (checkDisMissRightAway(settleTop))
            return
        viewDragHelperListener.onViewReleased(releasedChild.left, settleTop)
    }

    override fun onViewPositionChanged(
        changedView: View, left: Int, top: Int, dx: Int, dy: Int
    ) {
        super.onViewPositionChanged(changedView, left, top, dx, dy)
        if (startSwipeTime == 0L) startSwipeTime = System.currentTimeMillis()
        val percent = 1f - abs(top).toFloat() / screenHeight.toFloat()
        viewDragHelperListener.onSwipeChange(percent)
    }

    override fun onViewDragStateChanged(state: Int) {
        super.onViewDragStateChanged(state)
        updateDragStateChange(state, decorView.top)
    }
}