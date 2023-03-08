package com.dat.swipe_example.try_custom_swipe

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.dat.swipe_example.databinding.ActivityConfigOpenedBinding
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeListener

class ConfigOpenedActivity : Activity(), SwipeListener {
    companion object {
        const val TAG = "ActivityConfigOpened"
        const val CONFIG = "CONFIG"
    }

    private lateinit var binding: ActivityConfigOpenedBinding
    private lateinit var config: SwipeLayoutConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigOpenedBinding.inflate(layoutInflater)
        config = intent.getSerializableExtra(CONFIG) as SwipeLayoutConfig
        config.listener = this
        binding.root.setLateConfig(config)
        setContentView(binding.root)
        initView()


    }

    private fun initView() {
        binding.tvData.text = config.toString()
    }

    override fun onSwipeStateChanged(state: Int) {

    }

    override fun onSwipeChange(percent: Float) {

    }

    override fun onSwipeOpened() {}

    override fun onSwipeClosed() {
        finish()
    }

    override fun onApplyScrim(alpha: Float) {
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