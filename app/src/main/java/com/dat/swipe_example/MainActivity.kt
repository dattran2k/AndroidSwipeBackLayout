package com.dat.swipe_example

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dat.swipe_example.big_app.FacebookActivity
import com.dat.swipe_example.big_app.TelegramActivity
import com.dat.swipe_example.big_app.TiktokActivity
import com.dat.swipe_example.databinding.ActivityMainBinding
import com.dat.swipe_example.shared_element.SharedElementActivity
import com.dat.swipe_example.swipe_fragment.SwipeFragmentActivity
import com.dat.swipe_example.try_custom_swipe.ConfigOpenActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG = "MainActivity"
        fun getScreenWidth(): Int {
            return Resources.getSystem().displayMetrics.widthPixels
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(R.color.transparent)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.tvSwipeActivity.setOnClickListener {
            startActivity(Intent(this, SharedElementActivity::class.java))
        }
        binding.tvSwipeFragment.setOnClickListener {
            startActivity(Intent(this, SwipeFragmentActivity::class.java))
        }
        binding.tvSwipeTryYourCustom.setOnClickListener {
            startActivity(Intent(this, ConfigOpenActivity::class.java))
        }
        binding.imTelegram.setOnClickListener {
            startActivity(Intent(this, TelegramActivity::class.java))
        }
        binding.imFacebook.setOnClickListener {
            startActivity(Intent(this, FacebookActivity::class.java))
        }
        binding.imTiktok.setOnClickListener {
            startActivity(Intent(this, TiktokActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: ")
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop: ")
        EventBus.getDefault().unregister(this)
    }

    // I think event bus is not the good way to do this
    // you guy can try better pattern, this is ony for example
    // read about eventbus here https://github.com/greenrobot/EventBus
    @Subscribe
    fun onEvent(updateScalePercent: UpdateScalePercent) {
        when (updateScalePercent.from) {
            FacebookActivity::class.java -> {
                // when scroll , percent will be from 1.0f down to 0f
                // so we need to reverse it in this case so it will be from 0f -> 1f
                // I wanna make it from 0.8f -> 1f
                // in real app, you should make percent call slower, like update 0.01 percent once or 0.05
                // update really fast can make your app lagging
                val minScale = 0.9f
                val scaleThreshold = 0.55f
                var realPercent = (1 - updateScalePercent.percent) / (1 - scaleThreshold)
                if (realPercent > 1f)
                    realPercent = 1f
                var scale = realPercent * (1f - minScale) + minScale
                // care ful with this line
                if(updateScalePercent.percent == 1f)
                    scale = 1f
//                val scale = 0.9f + abs(1f - minScale) * percentReverse
                updateRootScale(scale)
            }
            TelegramActivity::class.java -> {
                // when scroll , percent will be from 1.0f down to 0f
                // so we need to reverse it in this case so it will be from 0f -> 1f
                // I wanna make it from 0.8f -> 1f
                // in real app, you should make percent call slower, like update 0.01 percent once or 0.05
                // update really fast can make your app lagging
                val maxMove = 0.2f
                val percent = updateScalePercent.percent * maxMove
                var widthX = percent * getScreenWidth()
                // care ful with this line
                if(updateScalePercent.percent == 1f)
                    widthX = 0f
                updateRootPosition(-widthX)
            }

        }

    }

    fun updateRootPosition(x: Float) {
        binding.container.x = x
    }

    fun updateRootScale(scale: Float) {
        binding.container.scaleX = scale
        binding.container.scaleY = scale
    }

}