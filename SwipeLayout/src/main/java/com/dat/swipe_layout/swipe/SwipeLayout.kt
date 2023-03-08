package com.dat.swipe_layout.swipe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.dat.swipe_layout.model.*
import com.dat.swipe_layout.swipe.view_drag_callback.*


class SwipeLayout : FrameLayout, ViewDragHelperListener {
    companion object {
        private const val MIN_FLING_VELOCITY = 400 // dips per second
        const val TAG = "SwipeLayout"
        private fun toAlpha(percentage: Float): Int {
            return (percentage * 255).toInt()
        }
    }

    private var screenWidth = 0
    private var screenHeight = 0
    private var decorView: View? = null
    private lateinit var dragHelper: ViewDragHelper

    private var isLocked = false
    private var isEdgeTouched = false
    private var startSwipeTime = 0L
    private var config: SwipeLayoutConfig = SwipeLayoutConfig.Builder().build()
    private var decorViewLeftOffset = 0

    // scrim
    private lateinit var scrimPaint: Paint
    private lateinit var scrimRenderer: ScrimRenderer

    constructor(context: Context) : super(context)
    constructor(context: Context, decorView: View, config: SwipeLayoutConfig?) : super(context) {
        this.decorView = decorView
        this.config = config ?: SwipeLayoutConfig.Builder().build()
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 1) throw IllegalArgumentException("child must be single layout")
        decorView = getChildAt(0)
        init()
    }

    fun setLateConfig(config: SwipeLayoutConfig) {
        this.config = config
        setConfigCallback()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        config.touchDisabledViews?.forEach { view ->
            if (isPointInsideView(ev.rawX, ev.rawY, view)) {
                Log.i(TAG, "not intercepting event")
                return false
            }
        }
        val touchSwipeView = config.touchSwipeViews
        if (touchSwipeView != null) {
            var isSwipeAble = false
            touchSwipeView.forEach { view ->
                if (isPointInsideView(ev.rawX, ev.rawY, view)) {
                    unlock()
                    isSwipeAble = true
                    return@forEach
                }
            }
            // if not touch swipe view, you can't swipe any more
            if (!isSwipeAble) {
                Log.i(TAG, "disabled swipe")
                lock()
            }
        }
        if (isLocked) {
            return false
        }
        if (config.isEdgeOnly) {
            isEdgeTouched = canDragFromEdge(ev)
        }

        val interceptForDrag: Boolean = try {
            dragHelper.shouldInterceptTouchEvent(ev)
        } catch (e: Exception) {
            false
        }
        return interceptForDrag && !isLocked
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isLocked) {
            return false
        }
        try {
            dragHelper.processTouchEvent(event)
        } catch (e: IllegalArgumentException) {
            return false
        }
        return true
    }


    override fun computeScroll() {
        super.computeScroll()
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        } else {
            decorViewLeftOffset = decorView?.left ?: 0
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        decorView?.offsetLeftAndRight(decorViewLeftOffset)
    }

    override fun onDraw(canvas: Canvas) {
        scrimRenderer.render(canvas, config.swipeDirection, scrimPaint, config.isFullScreenScrim)
    }

    private fun setConfigCallback() {
        if(decorView == null)
            return
        val params = ViewDragHelperParams(decorView!!, config, screenWidth, screenHeight, this)
        val callback = when (config.swipeDirection) {
            SwipeDirection.LEFT_TO_RIGHT -> LeftToRightCallback(params)
            SwipeDirection.RIGHT_TO_LEFT -> RightToLeftCallback(params)
            SwipeDirection.TOP_TO_BOTTOM -> TopToBottomCallback(params)
            SwipeDirection.BOTTOM_TO_TOP -> BottomToTopCallback(params)
            SwipeDirection.VERTICAL -> VerticalCallback(params)
            SwipeDirection.HORIZONTAL -> HorizontalCallback(params)
            SwipeDirection.LEFT_TO_RIGHT_FACEBOOK -> LeftToRightFacebookCallback(params)
            SwipeDirection.FREE -> FreeCallbackCallback(params)
        }
        val density = resources.displayMetrics.density
        val minVel = MIN_FLING_VELOCITY * density
        dragHelper = ViewDragHelper.create(this, config.sensitivity, callback)
        dragHelper.minVelocity = minVel
        scrimPaint.color = config.scrimColor
        if (!config.isEnableScrim)
            scrimPaint.alpha = 0
    }

    private fun init() {

        setBackgroundColor(Color.TRANSPARENT)
        setWillNotDraw(false)
        screenWidth = resources.displayMetrics.widthPixels
        screenHeight = resources.displayMetrics.heightPixels
        // Setup the dimmer view
        scrimPaint = Paint()
        scrimRenderer = ScrimRenderer(this, decorView!!)
        setConfigCallback()
    }


    private fun canDragFromEdge(ev: MotionEvent): Boolean {
        val x = ev.x
        val y = ev.y
        return when (config.swipeDirection) {
            SwipeDirection.LEFT_TO_RIGHT -> x < config.getEdgeSize(width.toFloat())
            SwipeDirection.RIGHT_TO_LEFT -> x > width - config.getEdgeSize(width.toFloat())
            SwipeDirection.BOTTOM_TO_TOP -> y > height - config.getEdgeSize(height.toFloat())
            SwipeDirection.TOP_TO_BOTTOM -> y < config.getEdgeSize(height.toFloat())
            SwipeDirection.HORIZONTAL -> x < config.getEdgeSize(width.toFloat()) ||
                    x > width - config.getEdgeSize(width.toFloat())
            SwipeDirection.VERTICAL -> y < config.getEdgeSize(height.toFloat()) ||
                    y > height - config.getEdgeSize(height.toFloat())
            SwipeDirection.LEFT_TO_RIGHT_FACEBOOK -> x < config.getEdgeSize(width.toFloat())
            SwipeDirection.FREE -> y < config.getEdgeSize(height.toFloat()) ||
                    y > height - config.getEdgeSize(height.toFloat()) ||
                    x < config.getEdgeSize(width.toFloat()) ||
                    x > width - config.getEdgeSize(width.toFloat())
        }
    }

    private fun applyScrim(percent: Float) {
        if (!config.isEnableScrim) return
        var realPercent = (percent - config.scrimThreshHold) / (1 - config.scrimThreshHold)
        if (realPercent < 0) realPercent = 0.0F
        var alpha =
            realPercent * (config.scrimStartAlpha - config.scrimEndAlpha) + config.scrimEndAlpha
        if (realPercent == 1f)
            alpha = 1f
        scrimPaint.alpha = toAlpha(alpha)
        config.listener?.onApplyScrim(alpha)
        invalidate(scrimRenderer.getDirtyRect(config.swipeDirection, config.isFullScreenScrim))
    }

    fun lock() {
//        dragHelper.abort()
        isLocked = true
    }

    fun unlock() {
//        dragHelper.abort()
        isLocked = false
    }

    private fun isPointInsideView(x: Float, y: Float, view: View): Boolean {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewX = location[0]
        val viewY = location[1]
        //point is inside view bounds
        return x > viewX && x < viewX + view.width && y > viewY && y < viewY + view.height
    }


    override fun onDragStateChanged(state: Int, vararg position: Int?) {
        config.listener?.onSwipeStateChanged(state)
        when (state) {
            ViewDragHelper.STATE_IDLE -> {
                startSwipeTime = 0L
                if (position.all { it == 0 }) {
                    // State Open
                    config.listener?.onSwipeOpened()
                } else {
                    // State Closed
                    config.listener?.onSwipeClosed()
                }
            }
            ViewDragHelper.STATE_DRAGGING -> {}
            ViewDragHelper.STATE_SETTLING -> {}
        }
    }

    override fun onSwipeChange(percent: Float) {
        config.listener?.onSwipeChange(percent)
        // Update the dimmer alpha
        applyScrim(percent)
    }

    override fun onViewReleased(left: Int, top: Int) {
        dragHelper.settleCapturedViewAt(left, top)
        invalidate()
    }

    override fun isEdgeCase() = !config.isEdgeOnly || isEdgeTouched
}