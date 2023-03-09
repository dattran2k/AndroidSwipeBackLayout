package com.dat.swipe_layout.model

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.customview.widget.ViewDragHelper
import com.dat.swipe_layout.swipe.SwipeLayout

class SwipeLayoutConfig private constructor() : java.io.Serializable{

    /**
     * Get the touch sensitivity set in the [ViewDragHelper] when
     * creating it.
     *
     * @return the touch sensitivity
     */
    var sensitivity = 1f

    /**
     * Color of the background scrim
     *
     * @return the scrim color integer
     */
    var scrimColor = Color.BLACK

    /**
     * Get the start alpha value for when your view start scroll
     *
     * @return the start alpha value (0.0 to 1.0)
     */
    var scrimStartAlpha = 1f

    /**
     * Get the end alpha value for when the user almost swipes the activity off the screen
     *
     * @return the end alpha value (0.0 to 1.0)
     */
    var scrimEndAlpha = 0f

    /**
     * Get the velocity threshold at which the swipe action is completed regardless of offset
     * distance of the drag
     *
     * velocity of the pointer as it left the screen in pixels per second.
     * @return the velocity threshold
     */
    var velocityThreshold = 5000f

    /**
     * Get at what % of the screen is the minimum viable distance the activity has to be dragged
     * in-order to be slinged off the screen
     *
     * @return the distant threshold as a percentage of the screen size (width or height)
     */
    var distanceThreshold = 0.4f

    /**
     * Has the user configured Swipe to only catch at the edge of the screen ?
     *
     * @return true if is edge capture only
     * @see [SwipeLayoutConfig.edgeSize]
     */
    var isEdgeOnly = false

    /**
     * Get the size of the edge field that is catchable
     *
     * @return the size of the edge that is grabable
     * @see [SwipeLayoutConfig.isEdgeOnly]
     */
    var edgeSize = 0.18f

    /**
     * Get the size of the edge field that is catchable
     * @param sizeScreen maybe your screenWidth or screenHeight
     * @return the size of the edge that is grabable
     */
    fun getEdgeSize(sizeScreen: Float): Float {
        return edgeSize * sizeScreen
    }

    /**
     * when you touch on this view, you can't swipe at all
     *
     * @return list view diable
     */
    var touchDisabledViews: List<View>? = null

    /**
     * you can only swipe when you touch this view, example you wanna swipe your image only
     *
     * @return list view swipe only
     */
    var touchSwipeViews: List<View>? = null

    /**
     * Get the position of the slidable mechanism for this configuration. This is the direction on
     * the screen that the user can swipe the view away from
     *
     * @return the Swipe diection
     */
    var swipeDirection = SwipeDirection.LEFT_TO_RIGHT


    /**
     * Get the Swipe listener set by the user to respond to certain events in the sliding
     * mechanism.
     *
     * @return the Swipe listener
     */
    var listener: SwipeListener? = null
    /**
     * scrim only draw when scroll from 1f -> scrimThreshHold
     * */
    var scrimThreshHold: Float = 0f
    /**
     * this is for smoother for animation transition
     * */
    var isDismissRightAway: Boolean = false

    /**
     *
     * this flag for check auto draw scrim
     *
     * */
    var isEnableScrim: Boolean = true

    /**
     * when you swipe very fast, dismiss right away , millisecond
     * if( onViewReleased - onViewPositionChanged < timeQuickDismiss) -> dismiss()
     * */
    var timeQuickDismiss: Long? = null

    /**
     * When layout draw scrim, it will be draw full screen, but it may reduce app performance
     * useful when you activity or fragment transparent
     * */
    var isFullScreenScrim : Boolean = false
    /**
     * The Builder for this configuration class. This is the only way to create a
     * configuration
     */
    override fun toString(): String {
        return "SwipeLayoutConfig(sensitivity=$sensitivity, scrimColor=$scrimColor, scrimStartAlpha=$scrimStartAlpha, scrimEndAlpha=$scrimEndAlpha, velocityThreshold=$velocityThreshold, distanceThreshold=$distanceThreshold, isEdgeOnly=$isEdgeOnly, edgeSize=$edgeSize, touchDisabledViews=$touchDisabledViews, touchSwipeViews=$touchSwipeViews, swipeDirection=$swipeDirection, listener=$listener, scrimThreshHold=$scrimThreshHold, isDismissRightAway=$isDismissRightAway, isEnableScrim=$isEnableScrim, timeQuickDismiss=$timeQuickDismiss, isFullScreenScrim=$isFullScreenScrim)"
    }

    class Builder {
        private val config: SwipeLayoutConfig = SwipeLayoutConfig()

        /**
         * Set direction that your layout can swipe
         * */
        fun swipeDirection(swipeDirection: SwipeDirection): Builder {
            config.swipeDirection = swipeDirection
            return this
        }

        /**
         * set the touch sensitivity set in the [ViewDragHelper] when creating it.
         * @see ViewDragHelper.create
         */
        fun sensitivity(sensitivity: Float): Builder {
            config.sensitivity = sensitivity
            return this
        }
        /**
         * set the color of your scrim aka shadow when your swipe,
         * the color will be draw by the percent change of [SwipeLayout]
         * @see SwipeLayout.applyScrim
         * @param color your color @example : ContextCompat.getColor(context,R.color.black)
         * @see SwipeLayout.updateConfigCallback
         */
        fun scrimColor(@ColorInt color: Int): Builder {
            config.scrimColor = color
            return this
        }
        /**
         * Set the alpha of [SwipeLayoutConfig.scrimColor] when start swipe
         * @see SwipeLayout.applyScrim
         */
        fun scrimStartAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            config.scrimStartAlpha = alpha
            return this
        }
        /**
         * Set the alpha of [SwipeLayoutConfig.scrimColor] when end swipe
         * @see SwipeLayout.applyScrim
         */
        fun scrimEndAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            config.scrimEndAlpha = alpha
            return this
        }
        /**
         * Set the scrim only draw when swipe from 1f -> scrimThreshHold
         * */
        fun endScrimThreshHold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): Builder {
            config.scrimThreshHold = threshold
            return this
        }
        /**
         * Set the velocityThreshold when you swipe, it depends on how fast you swipe
         * that's is velocity
         * */
        fun velocityThreshold(threshold: Float): Builder {
            config.velocityThreshold = threshold
            return this
        }
        /**
         * Fragment that's check that if you want your view dismiss right away without swipe animation
         *
         * Example : when you scroll from left to right, when you release your hand
         * if it's reach dismiss condition,
         *
         * @param isDismissRightAway false : your view will continue swipe to the end of right screen
         *
         * @param isDismissRightAway  true : your view will stop
         * then call [SwipeListener.onSwipeClosed] both
         *
         * */
        fun isDismissRightAway(isDismissRightAway: Boolean): Builder {
            config.isDismissRightAway = isDismissRightAway
            return this
        }

        /**
         * Set % of the screen is the minimum viable distance the activity has to be dragged
         * in-order to be dismiss
         */
        fun distanceThreshold(@FloatRange(from = 0.1, to = 0.9) threshold: Float): Builder {
            config.distanceThreshold = threshold
            return this
        }
        /**
         * Set that the SwipeLayout can only swipe in edge
         * @param flag true : Your SwipeLayout can only swipe inside edge range by [SwipeLayoutConfig.edgeSize]
         * @param flag false : No
         */
        fun edge(flag: Boolean): Builder {
            config.isEdgeOnly = flag
            return this
        }
        /**
         * Set % of screen that you wanna grab
         */
        fun edgeSize(@FloatRange(from = 0.0, to = 1.0) edgeSize: Float): Builder {
            config.edgeSize = edgeSize
            return this
        }
        /**
         * set Time if you want dismiss your view when swipe in that Time
         * Example : You open an image screen, you want to swipe your image up and release your hand very fast,
         * it will dismiss right away
         * @param timeQuickDismiss your time in milliseconds, 100-200 is good, don't be too slow
         */
        fun quickDismiss(timeQuickDismiss: Long): Builder {
            config.timeQuickDismiss = timeQuickDismiss
            return this
        }
        /**
         * set if you want to draw scrim or not
         */
        fun isEnableScrim(isEnableScrim: Boolean): Builder {
            config.isEnableScrim = isEnableScrim
            return this
        }
        /**
         * set when layout draw scrim, it will be draw full screen, but it may reduce app performance
         * useful when you activity or fragment transparent
         * [SwipeDirection.FREE] is true by default
         * @param isFullScreenScrim true : will be draw all
         * @param isFullScreenScrim false : will draw by normal
         */
        fun isFullScreenScrim(isFullScreenScrim: Boolean): Builder {
            config.isFullScreenScrim = isFullScreenScrim
            return this
        }
        /**
         * set listener for [SwipeLayout]
         */
        fun listener(listener: SwipeListener?): Builder {
            config.listener = listener
            return this
        }

        fun touchDisabledViews(views: List<View>?): Builder {
            config.touchDisabledViews = views
            return this
        }

        fun touchSwipeViews(views: List<View>?): Builder {
            config.touchSwipeViews = views
            return this
        }

        fun build(): SwipeLayoutConfig {
            return config
        }

    }
}