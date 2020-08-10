package com.example.weather.helper

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

object ImageHelper {

    fun loadWeatherIcon(context: Context?, iconCode: String, imageView: AppCompatImageView) {
        context?.let { it ->
            Glide.with(it).load(
                "https://openweathermap.org/img/wn/" + iconCode + "@2x.png"
            ).into(imageView)
        }
    }
}