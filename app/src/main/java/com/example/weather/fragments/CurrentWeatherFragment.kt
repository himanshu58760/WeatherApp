package com.example.weather.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import com.example.weather.adapter.HourlyAdapter
import com.example.weather.helper.Constants
import com.example.weather.helper.ImageHelper
import com.example.weather.models.CurrentWeatherResponse
import com.example.weather.models.Hourly
import com.example.weather.network.Status
import com.example.weather.viewmodel.CurrentWeatherViewModel
import kotlinx.android.synthetic.main.current_fragment.*

class CurrentWeatherFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    private val viewModel by viewModels<CurrentWeatherViewModel>()
    private val hourlyList = mutableListOf<Hourly>()
    private val hourlyAdapter = HourlyAdapter(hourlyList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh.setOnRefreshListener { viewModel.fetchCurrentWeather() }

        tvUserName.text = getString(R.string.hi_user_name, MySharedPreferences.getUserName())

        rvHourly.adapter = hourlyAdapter

        viewModel.fetchCurrentWeather()

        initObservers()
        context?.getSharedPreferences(
            Constants.DEFAULT_SHARED_PREFS,
            Constants.DEFAAULT_MODE_SHARED_PREFS
        )?.registerOnSharedPreferenceChangeListener(this)
    }

    private fun initObservers() {
        viewModel.statusLiveData.observe(viewLifecycleOwner) {
            swipe_refresh.isRefreshing = !(it.status == Status.SUCCESS || it.status == Status.ERROR)
            if (it.status == Status.ERROR)
                Toast.makeText(
                    context,
                    it.message ?: getString(R.string.some_error_occoured),
                    Toast.LENGTH_SHORT
                ).show()
        }

        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner) {
            updateUi(it)
        }
    }

    private fun updateUi(currentWeatherResponse: CurrentWeatherResponse) {
        currentWeatherResponse.apply {
            group.visibility = View.VISIBLE
            val bool = MySharedPreferences.getUnits() == Constants.CELSIUS
            tvTemperature.text =
                getString(
                    if (bool) R.string.current_temp_value_celsius
                    else R.string.current_temp_value_fahrenheit, Math.round(current.temp).toInt()
                )

            tvTemperatureFelt.text =
                getString(
                    if (bool) R.string.current_temp_value_celsius
                    else R.string.current_temp_value_fahrenheit,
                    Math.round(current.feels_like).toInt()
                )

            tvHumidity.text = getString(R.string.humidity_value, current.humidity)

            tvVisibility.text =
                getString(R.string.visibility_value, current.visibility.div(1000))

            tvWindSpeed.text = getString(
                if (bool) R.string.wind_speed_value_meteres_per_second
                else R.string.wind_speed_value_miles_per_hour
                , Math.round(current.wind_speed).toInt()
            )

            tvWeatherType.text = current.weather[0].main

            ImageHelper.loadWeatherIcon(context, current.weather[0].icon, ivWeatherType)

            hourlyList.clear()
            hourlyList.addAll(hourly)
            hourlyAdapter.notifyDataSetChanged()
        }
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        viewModel.fetchCurrentWeather()
    }
}