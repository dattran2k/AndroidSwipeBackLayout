package com.dat.swipe_example.big_app

import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.dat.swipe_example.R
import com.dat.swipe_example.UpdateScalePercent

import com.dat.swipe_example.databinding.ActivityTiktokBinding
import com.dat.swipe_layout.SwipeBackActivity
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeDirection
import org.greenrobot.eventbus.EventBus

class TiktokActivity : SwipeBackActivity() {
    companion object {
        const val TAG = "TiktokActivity"

    }

    private lateinit var binding: ActivityTiktokBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiktokBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Glide.with(this).asGif().load(R.raw.giphy).into(binding.image)

    }

    override fun onSwipeChange(percent: Float) {
        swipeChange(percent)
        EventBus.getDefault().post(UpdateScalePercent(percent, this::class.java))
    }

    private fun swipeChange(percent: Float) {
        // when scroll , percent will be from 1.0f down to 0f
        // so we need to reverse it in this case so it will be from 0f -> 1f
        // I wanna make it from 0.8f -> 1f
        // in real app, you should make percent call slower, like update 0.01 percent once or 0.05
        // update really fast can make your app lagging
        val minScale = 0f
        var scale = percent * (1 - minScale) + minScale
        if (percent == 1f)
            scale = 1f
        updateImageScale(scale)
    }

    override fun getSwipeConfig(): SwipeLayoutConfig {
        return super.getSwipeConfig().apply {
            touchSwipeViews = listOf(binding.image)
            swipeDirection = SwipeDirection.FREE
            scrimThreshHold = 0.7f
        }
    }

    fun updateImageScale(scale: Float) {
        binding.root.scaleX = scale
        binding.root.scaleY = scale
    }
    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
    }

}