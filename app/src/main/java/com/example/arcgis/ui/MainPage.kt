package com.example.arcgis.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.arcgis.R
import com.example.arcgis.ui.Map.MapActivity
import com.example.arcgis.ui.Slider.SliderView

class MainPage : AppCompatActivity() {
    lateinit var mapTask: CardView
    lateinit var sliderTask: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        initView()
        onClick()
    }

    private fun initView() {
        mapTask = findViewById(R.id.mapTask)
        sliderTask = findViewById(R.id.sliderTask)
    }

    private fun onClick() {
        mapTask.setOnClickListener {
            val intent = Intent(this , MapActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
        sliderTask.setOnClickListener {
            val intent = Intent(this , SliderView::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}