package com.dat.swipe_layout.model

import android.view.View

data class ViewDragHelperParams(
    val decorView: View,
    val config: SwipeLayoutConfig,
    val screenWidth: Int,
    val screenHeight: Int,
    val viewDragHelperListener: ViewDragHelperListener
)