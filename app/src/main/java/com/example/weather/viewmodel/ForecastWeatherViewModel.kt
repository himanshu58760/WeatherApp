package com.example.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weather.repository.ForecastWeatherRepository

class ForecastWeatherViewModel : ViewModel() {

    val statusLiveData = ForecastWeatherRepository.statusLiveData
    val forecastWeatherLiveData = ForecastWeatherRepository.forecastWeatherLiveData

    fun fetchForecastWeather() {
        ForecastWeatherRepository.fetchForecastWeather()
    }
}