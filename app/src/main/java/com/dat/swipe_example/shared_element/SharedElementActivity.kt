package com.dat.swipe_example.shared_element

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.dat.swipe_example.R
import com.dat.swipe_example.shared_element.adapter.GridAdapter
import com.dat.swipe_layout.SwipeBackActivity
import com.dat.swipe_example.databinding.ActivitySharedElementBinding
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeDirection

class SharedElementActivity : SwipeBackActivity() {
    companion object {
        const val TAG = "SharedElementActivity"
        var currentPosition = 0
    }
    private lateinit var binding: ActivitySharedElementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySharedElementBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.root.setLateConfig(getSwipeConfig())
        binding.recyclerView.adapter = GridAdapter(this)
    }
    override fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .swipeDirection(SwipeDirection.VERTICAL)
//            .distanceThreshold(0.3f)
            .scrimColor(ContextCompat.getColor(this, R.color.black))
            .endScrimThreshHold(0.55f)
            .scrimStartAlpha(1f)
            .build()
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