package com.dat.swipe_example.try_custom_swipe

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import com.dat.swipe_example.databinding.ActivityConfigOpenBinding
import com.dat.swipe_layout.SwipeBackActivity
import com.dat.swipe_layout.model.SwipeLayoutConfig
import com.dat.swipe_layout.model.SwipeDirection


// var scrimStartAlpha = 1f
// var scrimEndAlpha = 0f
// var distanceThreshold = 0.4f
// var scrimThreshHold: Float = 0f
//
// var position = swiperPosition.LEFT
// var isDismissRightAway: Boolean = false
// var isEnableScrim: Boolean = true
// var timeQuickDismiss: Long? = null
// var isFullScreenScrim : Boolean = false
class ConfigOpenActivity : SwipeBackActivity() {
    companion object {
        const val TAG = "ActivityConfigOpen"
    }

    val config = SwipeLayoutConfig.Builder().build()
    private lateinit var binding: ActivityConfigOpenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigOpenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        updateValue()
    }

    private fun updateValue() {
        binding.scrimStartAlpha.value.text = config.scrimStartAlpha.toString()
        binding.scrimEndAlpha.value.text = config.scrimEndAlpha.toString()
        binding.scrimThreshHold.value.text = config.scrimThreshHold.toString()
        binding.distanceThreshHold.value.text = config.distanceThreshold.toString()
        binding.edtTimeQuickDismiss.setText(if (config.timeQuickDismiss == null) "" else config.timeQuickDismiss.toString())
        binding.isDismissRightAway.value.text = config.isDismissRightAway.toString()
        binding.isEnableScrim.value.text = config.isEnableScrim.toString()
        binding.isFullScreenScrim.value.text = config.isFullScreenScrim.toString()
        binding.swipePosition.value.text = config.swipeDirection.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.btnStart.setOnClickListener {
            val intent = Intent(this, ConfigOpenedActivity::class.java)
            config.timeQuickDismiss =
                binding.edtTimeQuickDismiss.text.toString().toLongOrNull() ?: 0
            intent.putExtra(ConfigOpenedActivity.CONFIG, config)
            startActivity(intent)
        }
        // init scrimStartAlpha
        binding.scrimStartAlpha.let {
            it.title.text = "config.scrimStartAlpha : "
            it.seekBar.progress = ((config.scrimStartAlpha / 1f) * 100).toInt()
            it.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    config.scrimStartAlpha = getValue(1f, progress)
                    updateValue()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }
        // init scrimEndAlpha
        binding.scrimEndAlpha.let {
            it.title.text = "config.scrimEndAlpha : "
            it.seekBar.progress = ((config.scrimEndAlpha / 1f) * 100).toInt()
            it.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    config.scrimEndAlpha = getValue(1f, progress)
                    updateValue()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }
        // init scrimThreshHold
        binding.scrimThreshHold.let {
            it.title.text = "config.scrimThreshHold : "
            it.seekBar.progress = ((config.scrimThreshHold / 1f) * 100).toInt()
            it.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    config.scrimThreshHold = getValue(1f, progress)
                    updateValue()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }
        // init progress startScrimAlpha
        binding.distanceThreshHold.let {
            it.title.text = "config.distanceThreshHold : "
            it.seekBar.progress = ((config.distanceThreshold / 0.9f) * 100).toInt()
            it.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    config.distanceThreshold = getValue(0.9f, progress)
                    updateValue()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        }
        binding.swipePosition.let {
            it.title.text = "config.swipePosition : "
            addRadioButtons(
                it.radioGroup,
                SwipeDirection.values().toList()
            ) {
                config.swipeDirection = it
                updateValue()
            }
        }
        binding.isDismissRightAway.let {
            it.title.text = "config.isDismissRightAway : "
            addRadioButtons(it.radioGroup, listOf(true, false)) {
                config.isDismissRightAway = it
                updateValue()
            }
        }
        binding.isFullScreenScrim.let {
            it.title.text = "config.isFullScreenScrim : "
            addRadioButtons(it.radioGroup, listOf(true, false)) {
                config.isFullScreenScrim = it
                updateValue()
            }
        }
        binding.isEnableScrim.let {
            it.title.text = "config.isEnableScrim : "

            addRadioButtons(it.radioGroup, listOf(true, false)) {
                config.isEnableScrim = it
                updateValue()
            }
        }

    }

    fun <T> addRadioButtons(
        radioGroup: RadioGroup,
        list: List<T>,
        callback: (data: T) -> Unit
    ) {
        radioGroup.orientation = LinearLayout.VERTICAL
        for (element in list) {
            val rdbtn = RadioButton(this)
            rdbtn.id = View.generateViewId()
            rdbtn.text = element.toString()
            rdbtn.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked)
                    callback.invoke(element)
            }

            radioGroup.addView(rdbtn)
        }
    }

    fun getValue(max: Float, percent: Int): Float {
        return percent.toFloat() / 100 * max
    }


    override fun onSwipeStateChanged(state: Int) {

    }

    override fun onSwipeChange(percent: Float) {

    }

    override fun onSwipeOpened() {}

    override fun onSwipeClosed() {
//        binding.viewPager.background.alpha = 255
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onApplyScrim(alpha: Float) {
    }

    override fun getSwipeConfig(): SwipeLayoutConfig {
        val config = super.getSwipeConfig()
        config.touchDisabledViews = listOf(
            binding.scrimStartAlpha.seekBar,
            binding.scrimEndAlpha.seekBar,
            binding.scrimThreshHold.seekBar,
            binding.distanceThreshHold.seekBar,
        )
        return config
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