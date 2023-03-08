package com.dat.swipe_example.swipe_fragment.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View

import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat

import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dat.swipe_example.R
import com.dat.swipe_example.databinding.FragmentSecondBinding
import com.dat.swipe_example.swipe_fragment.NavigationManager
import com.dat.swipe_example.swipe_fragment.base_fragment.BaseSwipeableFragment
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeDirection

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment :
    BaseSwipeableFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.im,"hero_image")
        postponeEnterTransition()
        Glide.with(this)
            .load(R.drawable.beetle_562035)
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
            .into(binding.im)
        binding.im.setOnClickListener {
            NavigationManager.getInstance().openFragment(ThirdFragment())
        }
//        binding.im.startAnimation(scaleAnim)
    }

    override fun getSwipeConfig() = SwipeLayoutConfig.Builder()
        .listener(this)
        .swipeDirection(SwipeDirection.LEFT_TO_RIGHT)
        .scrimStartAlpha(1f)
        .quickDismiss(100)
        .scrimColor(ContextCompat.getColor(requireContext(), R.color.black))
        .build()

}