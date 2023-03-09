package com.dat.swipe_example.swipe_fragment.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dat.swipe_example.R
import com.dat.swipe_example.databinding.FragmentSecondBinding
import com.dat.swipe_example.swipe_fragment.NavigationManager
import com.dat.swipe_layout.SwipeBackFragment
import com.dat.swipe_layout.model.SwipeDirection
import com.dat.swipe_layout.model.SwipeLayoutConfig

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : SwipeBackFragment() {
    private var _binding: FragmentSecondBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater)


        return wrapSwipeLayout(binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.im, "hero_image")
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