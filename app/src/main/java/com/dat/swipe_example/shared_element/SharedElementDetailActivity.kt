package com.dat.swipe_example.shared_element

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dat.swipe_example.R
import com.dat.swipe_example.databinding.ActivitySharedElementDetailBinding
import com.dat.swipe_example.shared_element.adapter.ImageData.IMAGE_DRAWABLES
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeListener
import com.dat.swipe_layout.model.SwipeDirection

class SharedElementDetailActivity : AppCompatActivity(), SwipeListener {
    companion object {
        const val TAG = "SharedElementDetailActivity"
    }

    private lateinit var binding: ActivitySharedElementDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivitySharedElementDetailBinding.inflate(layoutInflater)
        binding.root.setLateConfig(getSwipeConfig())
        setContentView(binding.root)
        val imageRes = IMAGE_DRAWABLES[SharedElementActivity.currentPosition]
        binding.image.transitionName = imageRes.toString()
        postponeEnterTransition()
        Glide.with(this)
            .load(imageRes)
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                    // startPostponedEnterTransition() should also be called on it to get the transition
                    // going in case of a failure.
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                    // startPostponedEnterTransition() should also be called on it to get the transition
                    // going when the image is ready.
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(binding.image)
    }
    fun getSwipeConfig(): SwipeLayoutConfig {
        return SwipeLayoutConfig.Builder()
            .listener(this)
            .swipeDirection(SwipeDirection.FREE)
            .isFullScreenScrim(true)
                // when set swiper to swiperPosition.FREE, it's always dismissRightAway
                // because se I have no idea where should it scroll to
                // so no need to set this,
            .isDismissRightAway(true)
            .distanceThreshold(0.3f)
            .endScrimThreshHold(0.7f)
            .touchSwipeViews(listOf(binding.image))
            .quickDismiss(100)
            .scrimColor(ContextCompat.getColor(this, R.color.black))
            .scrimStartAlpha(0.9f)
            .build()
    }

    override fun onSwipeStateChanged(state: Int) {

    }

    override fun onSwipeChange(percent: Float) {
        val minScale = 0.4f
        var scale = percent * (1 - minScale) + minScale
        if (percent == 1f)
            scale = 1f
        updateImageScale(scale)
    }
    fun updateImageScale(scale: Float) {
        binding.image.scaleX = scale
        binding.image.scaleY = scale
    }
    override fun onSwipeOpened() {}

    override fun onSwipeClosed() {
        onBackPressedDispatcher.onBackPressed()
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