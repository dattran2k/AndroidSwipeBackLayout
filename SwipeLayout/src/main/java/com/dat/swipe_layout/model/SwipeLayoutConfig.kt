package com.dat.swipe_layout.model

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange

class SwipeLayoutConfig private constructor() : java.io.Serializable{

    /**
     * Get the touch 'width' to be used in the gesture detection. This value should incorporate with
     * the device's touch slop
     *
     * @return the touch area size
     */
    var touchSize = -1f

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
     */
    var isEdgeOnly = false

    /**
     * Get the size of the edge field that is catchable
     *
     * @return the size of the edge that is grabable
     * @see .isEdgeOnly
     */
    var edgeSize = 0.18f

    fun getEdgeSize(size: Float): Float {
        return edgeSize * size
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
     *
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
        return "SwipeConfig(touchSize=$touchSize\n sensitivity=$sensitivity\n scrimColor=$scrimColor\n scrimStartAlpha=$scrimStartAlpha\n scrimEndAlpha=$scrimEndAlpha\n velocityThreshold=$velocityThreshold\n distanceThreshold=$distanceThreshold\n isEdgeOnly=$isEdgeOnly\n edgeSize=$edgeSize\n touchDisabledViews=$touchDisabledViews\n touchSwipeViews=$touchSwipeViews\n position=$swipeDirection\n listener=$listener\n scrimThreshHold=$scrimThreshHold\n isDismissRightAway=$isDismissRightAway\n isEnableScrim=$isEnableScrim\n timeQuickDismiss=$timeQuickDismiss\n isFullScreenScrim=$isFullScreenScrim)"
    }

    class Builder {
        private val config: SwipeLayoutConfig = SwipeLayoutConfig()
        fun swipeDirection(swipeDirection: SwipeDirection): Builder {
            config.swipeDirection = swipeDirection
            return this
        }

        fun touchSize(size: Float): Builder {
            config.touchSize = size
            return this
        }

        fun sensitivity(sensitivity: Float): Builder {
            config.sensitivity = sensitivity
            return this
        }

        fun scrimColor(@ColorInt color: Int): Builder {
            config.scrimColor = color
            return this
        }

        fun scrimStartAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            config.scrimStartAlpha = alpha
            return this
        }

        fun scrimEndAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): Builder {
            config.scrimEndAlpha = alpha
            return this
        }

        fun endScrimThreshHold(@FloatRange(from = 0.0, to = 1.0) threshold: Float): Builder {
            config.scrimThreshHold = threshold
            return this
        }

        fun velocityThreshold(threshold: Float): Builder {
            config.velocityThreshold = threshold
            return this
        }

        fun isDismissRightAway(isDismissRightAway: Boolean): Builder {
            config.isDismissRightAway = isDismissRightAway
            return this
        }

        fun distanceThreshold(@FloatRange(from = 0.1, to = 0.9) threshold: Float): Builder {
            config.distanceThreshold = threshold
            return this
        }

        fun edge(flag: Boolean): Builder {
            config.isEdgeOnly = flag
            return this
        }

        fun quickDismiss(timeQuickDismiss: Long): Builder {
            config.timeQuickDismiss = timeQuickDismiss
            return this
        }

        fun isEnableScrim(isEnableScrim: Boolean): Builder {
            config.isEnableScrim = isEnableScrim
            return this
        }
        fun isFullScreenScrim(isFullScreenScrim: Boolean): Builder {
            config.isFullScreenScrim = isFullScreenScrim
            return this
        }
        fun edgeSize(@FloatRange(from = 0.0, to = 1.0) edgeSize: Float): Builder {
            config.edgeSize = edgeSize
            return this
        }

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
    class BuilderEmpty {
        private val config = SwipeLayoutConfig()
        fun build(): SwipeLayoutConfig {
            return config
        }
    }
}