package com.example.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import com.example.weather.helper.Constants
import com.example.weather.helper.ImageHelper
import com.example.weather.models.Hourly
import java.text.SimpleDateFormat
import java.util.*


class HourlyAdapter(val hourlyList: MutableList<Hourly>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        context = parent.context
        return HourlyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_data, parent, false)
        )
    }

    override fun getItemCount(): Int = hourlyList.size

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        holder.bind(hourlyList[position])
    }

    inner class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTime = itemView.findViewById<AppCompatTextView>(R.id.tvTime)
        private val textViewTemperature = itemView.findViewById<AppCompatTextView>(R.id.tvTemperatureHourly)
        private val textViewWeatherType = itemView.findViewById<AppCompatTextView>(R.id.tvWeatherTypeHourly)
        private val imageViewWeatherType = itemView.findViewById<AppCompatImageView>(R.id.ivWeatherTypeHourly)

        fun bind(hourly: Hourly) {
            textViewTemperature.text = context.getString(
                if (MySharedPreferences.getUnits() == Constants.CELSIUS) R.string.current_temp_value_celsius
                else R.string.current_temp_value_fahrenheit
                , Math.round(hourly.temp).toInt()
            )

            textViewWeatherType.text = hourly.weather[0].main
            ImageHelper.loadWeatherIcon(context, hourly.weather[0].icon, imageViewWeatherType)

            val time = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(hourly.dt * 1000)
            textViewTime.text = time
        }
    }
}
