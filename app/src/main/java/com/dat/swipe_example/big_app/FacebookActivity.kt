package com.dat.swipe_example.big_app

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.dat.swipe_example.R
import com.dat.swipe_example.UpdateScalePercent
import com.dat.swipe_example.databinding.ActivityFacebookBinding
import com.dat.swipe_layout.SwipeBackActivity
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeDirection
import org.greenrobot.eventbus.EventBus

class FacebookActivity : SwipeBackActivity() {
    companion object {
        const val TAG = "FacebookActivity"

    }
    private lateinit var binding: ActivityFacebookBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        binding = ActivityFacebookBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .swipeDirection(SwipeDirection.LEFT_TO_RIGHT_FACEBOOK)
//            .distanceThreshold(0.3f)
            .scrimColor(ContextCompat.getColor(this, R.color.black))
            .endScrimThreshHold(0.55f)
            .scrimStartAlpha(1f)
//            .edgeSize(0.8f)
//            .edge(true)
            .build()
    }

    override fun onSwipeChange(percent: Float) {

        EventBus.getDefault().post(UpdateScalePercent(percent,this::class.java))
    }

    override fun onApplyScrim(alpha: Float) {
        super.onApplyScrim(alpha)
    }
    override fun onPause() {
        super.onPause()
//        overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
        Log.e(TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
    }

}