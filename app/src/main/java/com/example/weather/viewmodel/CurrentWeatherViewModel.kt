package com.example.weather.viewmodel

import androidx.lifecycle.ViewModel
import com.example.news.Helper.MyApplication
import com.example.weather.helper.LocationHelper
import com.example.weather.repository.CurrentWeatherRepository

class CurrentWeatherViewModel : ViewModel() {
    val currentWeatherLiveData = CurrentWeatherRepository.currentWeatherLiveData
    val statusLiveData = CurrentWeatherRepository.statusLiveData

    fun fetchCurrentWeather() {
        CurrentWeatherRepository.fetchCurrentWeather()
    }
}