package com.dat.swipe_layout.swipe.view_drag_callback

import android.view.View
import com.dat.swipe_layout.model.ViewDragHelperParams
import kotlin.math.abs
/**
 * The drag helper callbacks for dragging the swiper attachment in both horizontal directions
 */
class HorizontalCallback(
    params: ViewDragHelperParams
) : BaseViewDragHelperCallback(params) {

    override fun tryCaptureView(child: View, pointerId: Int): Boolean {

        return child.id == decorView.id && viewDragHelperListener.isEdgeCase()
    }

    override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
        return clamp(left, getClampLeft(), getClamRight())
    }

    override fun getViewHorizontalDragRange(child: View): Int {
        return screenWidth
    }

    override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
        super.onViewReleased(releasedChild, xvel, yvel)
        val settleLeft = getSettle(
            releasedChild.left, xvel, yvel, screenWidth, true
        )
        if (checkDisMissRightAway(settleLeft))
            return
        viewDragHelperListener.onViewReleased(settleLeft, releasedChild.top)
    }

    override fun onViewPositionChanged(
        changedView: View, left: Int, top: Int, dx: Int, dy: Int
    ) {
        super.onViewPositionChanged(changedView, left, top, dx, dy)

        val percent = 1f - abs(left).toFloat() / screenWidth.toFloat()
        viewDragHelperListener.onSwipeChange(percent)
    }

    override fun onViewDragStateChanged(state: Int) {
        super.onViewDragStateChanged(state)
         viewDragHelperListener.onDragStateChanged(state, decorView.left)
    }
}