package com.example.arcgis.ui.Slider

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.arcgis.R
import kotlinx.android.synthetic.main.activity_main_page.*


class SliderView : AppCompatActivity() {
    lateinit var imageSlider: ImageSlider

    lateinit var expandedState: CardView
    lateinit var collapsedState: CardView
    lateinit var hiddenState: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider_view)

        initView()
        createSlider()
        onClick()
    }

    fun initView() {
        imageSlider = findViewById(R.id.image_slider)

        expandedState = findViewById(R.id.state1)
        collapsedState = findViewById(R.id.state2)
        hiddenState = findViewById(R.id.state3)
    }

    fun onClick() {
        expandedState.setOnClickListener() {
            val targetHeight: Int =
                imageSlider.height + ((imageSlider.height * 75) / 100)
            expand(imageSlider, 2000, targetHeight)
        }
        collapsedState.setOnClickListener() {
            val targetHeight: Int =
                imageSlider.height / 2
            collapse(imageSlider, 2000, targetHeight)
        }
        hiddenState.setOnClickListener() {
            collapse(imageSlider, 2000, 0)
        }
    }


    fun createSlider() {
        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel("https://bit.ly/37Rn50u", "Baby Owl", ScaleTypes.FIT))
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2",
                "Elephants and tigers may become extinct.",ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/3fLJf72",
                "The population of elephants is decreasing in the world.",ScaleTypes.FIT
            )
        )
        imageList.add(SlideModel("https://bit.ly/37Rn50u", "Baby Owl 2", ScaleTypes.FIT))
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2",
                "Elephants and tigers may become extinct. 2", ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/3fLJf72",
                "The population of elephants is decreasing in the world. 2", ScaleTypes.FIT
            )
        )
        imageList.add(SlideModel("https://bit.ly/37Rn50u", "Baby Owl 3",  ScaleTypes.FIT))
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2",
                "Elephants and tigers may become extinct. 3", ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/3fLJf72",
                "The population of elephants is decreasing in the world. 3", ScaleTypes.FIT
            )
        )
        imageList.add(SlideModel("https://bit.ly/37Rn50u", "Baby Owl 4", ScaleTypes.FIT))
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2",
                "Elephants and tigers may become extinct. 4", ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/3fLJf72",
                "The population of elephants is decreasing in the world. 4", ScaleTypes.FIT
            )
        )
        imageList.add(SlideModel("https://bit.ly/37Rn50u", "Baby Owl 4",  ScaleTypes.FIT))
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2",
                "Elephants and tigers may become extinct. 5", ScaleTypes.FIT
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/3fLJf72",
                "The population of elephants is decreasing in the world. 5", ScaleTypes.FIT
            )
        )

        imageSlider.setImageList(imageList)


    }

    fun expand(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        v.setVisibility(View.VISIBLE)
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.getLayoutParams().height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }

    fun collapse(v: View, duration: Int, targetHeight: Int) {
        val prevHeight = v.height
        val valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }
}