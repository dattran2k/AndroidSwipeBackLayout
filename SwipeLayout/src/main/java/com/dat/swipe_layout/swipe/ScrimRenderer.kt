package com.dat.swipe_layout.swipe

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import com.dat.swipe_layout.model.SwipeDirection

internal class ScrimRenderer(private val rootView: View, private val decorView: View) {
    private val dirtyRect: Rect = Rect()

    fun render(canvas: Canvas, position: SwipeDirection, paint: Paint, fullScreenScrim: Boolean) {
        if (fullScreenScrim)
            return renderFreeAll(canvas, paint)
        when (position) {
            SwipeDirection.FREE -> renderFreeAll(canvas, paint)
            SwipeDirection.LEFT_TO_RIGHT -> renderLeft(canvas, paint)
            SwipeDirection.LEFT_TO_RIGHT_FACEBOOK -> renderHorizontal(canvas, paint)
            SwipeDirection.RIGHT_TO_LEFT -> renderRight(canvas, paint)
            SwipeDirection.TOP_TO_BOTTOM -> renderTop(canvas, paint)
            SwipeDirection.BOTTOM_TO_TOP -> renderBottom(canvas, paint)
            SwipeDirection.VERTICAL -> renderVertical(canvas, paint)
            SwipeDirection.HORIZONTAL -> renderHorizontal(canvas, paint)
        }
    }

    fun getDirtyRect(position: SwipeDirection, fullScreenScrim: Boolean): Rect {
        if (fullScreenScrim) {
            dirtyRect.set(0, 0, rootView.measuredWidth, rootView.measuredHeight)
            return dirtyRect
        }
        when (position) {
            SwipeDirection.LEFT_TO_RIGHT -> dirtyRect.set(
                0,
                0,
                decorView.left,
                rootView.measuredHeight
            )
            SwipeDirection.RIGHT_TO_LEFT -> dirtyRect.set(
                decorView.right,
                0,
                rootView.measuredWidth,
                rootView.measuredHeight
            )
            SwipeDirection.TOP_TO_BOTTOM -> dirtyRect.set(
                0,
                0,
                rootView.measuredWidth,
                decorView.top
            )
            SwipeDirection.BOTTOM_TO_TOP -> dirtyRect.set(
                0,
                decorView.bottom,
                rootView.measuredWidth,
                rootView.measuredHeight
            )
            SwipeDirection.VERTICAL -> if (decorView.top > 0) {
                dirtyRect.set(0, 0, rootView.measuredWidth, decorView.top)
            } else {
                dirtyRect.set(0, decorView.bottom, rootView.measuredWidth, rootView.measuredHeight)
            }
            SwipeDirection.HORIZONTAL, SwipeDirection.LEFT_TO_RIGHT_FACEBOOK -> if (decorView.left > 0) {
                dirtyRect.set(0, 0, decorView.left, rootView.measuredHeight)
            } else {
                dirtyRect.set(decorView.right, 0, rootView.measuredWidth, rootView.measuredHeight)
            }
            SwipeDirection.FREE -> {
                dirtyRect.set(0, 0, rootView.measuredWidth, rootView.measuredHeight)
            }
        }
        return dirtyRect
    }

    private fun renderLeft(canvas: Canvas, paint: Paint) {
        canvas.drawRect(0f, 0f, decorView.left.toFloat(), rootView.measuredHeight.toFloat(), paint)
    }

    private fun renderRight(canvas: Canvas, paint: Paint) {
        canvas.drawRect(
            decorView.right.toFloat(),
            0f,
            rootView.measuredWidth.toFloat(),
            rootView.measuredHeight.toFloat(),
            paint
        )
    }

    private fun renderTop(canvas: Canvas, paint: Paint) {
        canvas.drawRect(0f, 0f, rootView.measuredWidth.toFloat(), decorView.top.toFloat(), paint)
    }

    private fun renderBottom(canvas: Canvas, paint: Paint) {
        canvas.drawRect(
            0f,
            decorView.bottom.toFloat(),
            rootView.measuredWidth.toFloat(),
            rootView.measuredHeight.toFloat(),
            paint
        )
    }

    private fun renderVertical(canvas: Canvas, paint: Paint) {
        if (decorView.top > 0) {
            renderTop(canvas, paint)
        } else {
            renderBottom(canvas, paint)
        }
    }

    private fun renderFreeAll(canvas: Canvas, paint: Paint) {
        canvas.drawRect(
            0f,
            0f,
            rootView.measuredWidth.toFloat(),
            rootView.measuredHeight.toFloat(),
            paint
        )
    }

    private fun renderHorizontal(canvas: Canvas, paint: Paint) {
        if (decorView.left > 0) {
            renderLeft(canvas, paint)
        } else {
            renderRight(canvas, paint)
        }
    }
}