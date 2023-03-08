package com.dat.swipe_layout.model

interface ViewDragHelperListener {
    fun onDragStateChanged(state: Int,vararg position: Int?)
    fun onSwipeChange(percent: Float)
    fun onViewReleased(left :Int,top : Int)
    fun isEdgeCase() : Boolean
}