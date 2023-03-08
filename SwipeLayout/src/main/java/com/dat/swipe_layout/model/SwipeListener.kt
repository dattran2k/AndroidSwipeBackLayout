package com.dat.swipe_layout.model

import androidx.customview.widget.ViewDragHelper

interface SwipeListener {
    /**
     * This is called when the [ViewDragHelper] calls it's
     * state change callback.
     *
     * @see ViewDragHelper.STATE_IDLE
     *
     * @see ViewDragHelper.STATE_DRAGGING
     *
     * @see ViewDragHelper.STATE_SETTLING
     *
     *
     * @param state the [ViewDragHelper] state
     */
    fun onSwipeStateChanged(state: Int)
    fun onSwipeChange(percent: Float)
    fun onSwipeOpened()

    fun onSwipeClosed()

    fun onApplyScrim(alpha: Float) {}
}