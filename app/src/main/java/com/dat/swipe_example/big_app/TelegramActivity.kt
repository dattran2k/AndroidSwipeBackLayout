package com.dat.swipe_example.big_app

import android.os.Bundle
import android.util.Log
import com.dat.swipe_example.R
import com.dat.swipe_example.UpdateScalePercent
import com.dat.swipe_example.databinding.ActivityTelegramBinding
import com.dat.swipe_layout.SwipeBackActivity
import com.dat.swipe_layout.model.SwipeLayoutConfig
import org.greenrobot.eventbus.EventBus

class TelegramActivity : SwipeBackActivity() {
    companion object {
        const val TAG = "TelegramActivity"

    }

    private lateinit var binding: ActivityTelegramBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        binding = ActivityTelegramBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun getSwipeConfig(): SwipeLayoutConfig {
        return super.getSwipeConfig().apply {
            edgeSize = 0.8f
            isEdgeOnly = true
        }
    }

    override fun onSwipeChange(percent: Float) {
        EventBus.getDefault().post(UpdateScalePercent(percent, this::class.java))
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        Log.e(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
    }

}