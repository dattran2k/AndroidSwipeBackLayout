package com.dat.swipe_layout

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions

// I take it from : http://www.java2s.com/example/android/android.app/activity-property-index.html
object SwipeUtils {
    /** set activity translucent and transparent background
    * or set on theme :
    * <item name="android:windowIsTranslucent">true</item>-->
    *  <item name="android:windowBackground">#00FFFFFF</item> ( transparent color )
    */
    fun initThemeSwipe(activity: Activity){
        convertActivityToTranslucent(activity)
        activity.window.setBackgroundDrawableResource(android.R.color.transparent)

    }
    /**
    * Convert a translucent themed Activity
    * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
    * Activity.
    * <p>
    * Call this whenever the background of a translucent Activity has changed
    * to become opaque. Doing so will allow the {@link android.view.Surface} of
    * the Activity behind to be released.
    * <p>
    * This call has no effect on non-translucent activities or on activities
    * with the {@link android.R.attr#windowIsFloating} attribute.
    */
    fun convertActivityFromTranslucent(activity: Activity?) {
        try {
            val method = Activity::class.java
                .getDeclaredMethod("convertFromTranslucent")
            method.isAccessible = true
            method.invoke(activity)
        } catch (t: Throwable) {
        }
    }

    /**
    * Convert a translucent themed Activity
    * {@link android.R.attr#windowIsTranslucent} back from opaque to
    * translucent following a call to
    * {@link #convertActivityFromTranslucent(android.app.Activity)} .
    * <p>
    * Calling this allows the Activity behind this one to be seen again. Once
    * all such Activities have been redrawn
    * <p>
    * This call has no effect on non-translucent activities or on activities
    * with the {@link android.R.attr#windowIsFloating} attribute.
    */
    @SuppressLint("DiscouragedPrivateApi")
    fun convertActivityToTranslucent(activity: Activity?) {
        try {
            val getActivityOptions = Activity::class.java.getDeclaredMethod("getActivityOptions")
            getActivityOptions.isAccessible = true
            val options = getActivityOptions.invoke(activity)
            val classes = Activity::class.java.declaredClasses
            var translucentConversionListenerClazz: Class<*>? = null
            for (clazz in classes) {
                if (clazz.simpleName.contains("TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz
                }
            }
            val convertToTranslucent = Activity::class.java.getDeclaredMethod(
                "convertToTranslucent",
                translucentConversionListenerClazz, ActivityOptions::class.java
            )
            convertToTranslucent.isAccessible = true
            convertToTranslucent.invoke(activity, null, options)
        } catch (t: Throwable) {
        }
    }
}