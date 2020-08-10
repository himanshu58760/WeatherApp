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
import com.example.weather.models.Daily
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(val dailyList: List<Daily>) :
    RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        context = parent.context
        return DailyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        )
    }

    override fun getItemCount(): Int = dailyList.size

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(dailyList[position])
    }

    inner class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDate = itemView.findViewById<AppCompatTextView>(R.id.tvDateDaily)
        private val textViewWeatherTypeDaily =
            itemView.findViewById<AppCompatTextView>(R.id.tvWeatherTypeDaily)
        private val textViewTemperatureMinMaxDaily =
            itemView.findViewById<AppCompatTextView>(R.id.tvTemperatureMinMaxDaily)
        private val imageViewWeatherTypeDaily =
            itemView.findViewById<AppCompatImageView>(R.id.ivWeatherTypeDaily)

        fun bind(daily: Daily) {
            textViewDate.text = SimpleDateFormat("dd/MM", Locale.getDefault()).format(daily.dt * 1000)
            textViewWeatherTypeDaily.text = daily.weather[0].main
            textViewTemperatureMinMaxDaily.text = context.getString(
                if (MySharedPreferences.getUnits() == Constants.CELSIUS) R.string.temp_min_max_value_celsius
                else R.string.temp_min_max_value_fahrenheit
                , Math.round(daily.temp.min).toInt(), Math.round(daily.temp.max).toInt()
            )

            ImageHelper.loadWeatherIcon(context, daily.weather[0].icon, imageViewWeatherTypeDaily)
        }
    }
}
