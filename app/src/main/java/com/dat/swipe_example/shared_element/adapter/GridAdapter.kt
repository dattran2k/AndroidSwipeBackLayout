package com.dat.swipe_example.shared_element.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dat.swipe_example.R
import com.dat.swipe_example.shared_element.SharedElementActivity
import com.dat.swipe_example.shared_element.SharedElementActivity.Companion.currentPosition
import com.dat.swipe_example.shared_element.SharedElementDetailActivity
import java.util.concurrent.atomic.AtomicBoolean


/**
 * A fragment for displaying a grid of images.
 */
class GridAdapter(sharedElementActivity: SharedElementActivity) :
    RecyclerView.Adapter<GridAdapter.ImageViewHolder?>() {
    /**
     * A listener that is attached to all ViewHolders to handle image loading events and clicks.
     */
    interface ViewHolderListener {
        fun onLoadCompleted(view: ImageView?, adapterPosition: Int)
        fun onItemClicked(view: View?, adapterPosition: Int)
    }

    private val requestManager: RequestManager
    private val viewHolderListener: ViewHolderListener

    /**
     * Constructs a new grid adapter for the given [Fragment].
     */
    init {
        requestManager = Glide.with(sharedElementActivity)
        viewHolderListener = ViewHolderListenerImpl(sharedElementActivity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_card, parent, false)
        return ImageViewHolder(view, requestManager, viewHolderListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount(): Int {
        return ImageData.IMAGE_DRAWABLES.size
    }

    /**
     * Default [ViewHolderListener] implementation.
     */
    class ViewHolderListenerImpl constructor(private val sharedElementActivity: SharedElementActivity) :
        ViewHolderListener {
        private val enterTransitionStarted: AtomicBoolean = AtomicBoolean()

        override fun onLoadCompleted(view: ImageView?, position: Int) {
            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
            if (currentPosition != position) {
                return
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return
            }
            sharedElementActivity.startPostponedEnterTransition()
        }

        /**
         * Handles a view click by setting the current position to the given `position` and
         * starting a [ImagePagerFragment] which displays the image at the position.
         *
         * @param view     the clicked [ImageView] (the shared element view will be re-mapped at the
         * GridFragment's SharedElementCallback)
         * @param position the selected view position
         */
        override fun onItemClicked(view: View?, position: Int) {
            // Update the position.
            currentPosition = position

            // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
            // instead of fading out with the rest to prevent an overlapping animation of fade and move). )
            val transitioningView = view?.findViewById<ImageView>(R.id.card_image)
            val intent = Intent(sharedElementActivity, SharedElementDetailActivity::class.java)
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                sharedElementActivity,
                transitioningView!!,
                transitioningView.transitionName
            )
            sharedElementActivity.startActivity(intent,options.toBundle())
        }
    }

    /**
     * ViewHolder for the grid's images.
     */
    inner class ImageViewHolder(
        itemView: View, requestManager: RequestManager,
        viewHolderListener: ViewHolderListener
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView
        private val requestManager: RequestManager
        private val viewHolderListener: ViewHolderListener

        init {
            image = itemView.findViewById(R.id.card_image)
            this.requestManager = requestManager
            this.viewHolderListener = viewHolderListener
            itemView.findViewById<View>(R.id.card_view).setOnClickListener(this)
        }

        /**
         * Binds this view holder to the given adapter position.
         *
         *
         * The binding will load the image into the image view, as well as set its transition name for
         * later.
         */
        fun onBind() {
            val adapterPosition = adapterPosition
            setImage(adapterPosition)
            // Set the string value of the image resource as the unique transition name for the view.
            image.transitionName = ImageData.IMAGE_DRAWABLES[adapterPosition].toString()
        }

        fun setImage(adapterPosition: Int) {
            // Load the image with Glide to prevent OOM error when the image drawables are very large.
            requestManager
                .load(ImageData.IMAGE_DRAWABLES[adapterPosition])
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?, model: Any,
                        target: Target<Drawable?>, isFirstResource: Boolean
                    ): Boolean {
                        viewHolderListener.onLoadCompleted(image, adapterPosition)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        viewHolderListener.onLoadCompleted(image, adapterPosition)
                        return false
                    }
                })
                .into(image)
        }

        override fun onClick(view: View) {
            // Let the listener start the ImagePagerFragment.
            viewHolderListener.onItemClicked(view, adapterPosition)
        }
    }
}