package com.example.nasaphotooftheday.network

import com.example.weather.helper.Constants
import com.example.weather.models.CurrentWeatherResponse
import com.example.weather.models.ForecastWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("data/2.5/onecall")
    fun fetchCurrentWeather(
        @Query("lat") lat: Double, @Query("lon") long: Double,
        @Query("units") units: String, @Query("appid") country: String = Constants.API_KEY,
        @Query("exclude") exclude: String = "minutely,daily"
    ): Call<CurrentWeatherResponse>

    @GET("data/2.5/onecall")
    fun fetchForecastWeather(
        @Query("lat") lat: Double, @Query("lon") long: Double,
        @Query("units") units: String, @Query("appid") country: String = Constants.API_KEY,
        @Query("exclude") exclude: String = "minutely,hourly,current"
    ): Call<ForecastWeatherResponse>
}