package com.example.weather.repository

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasaphotooftheday.network.ApiClient
import com.example.news.Helper.MyApplication
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import com.example.weather.helper.LocationHelper
import com.example.weather.models.ForecastWeatherResponse
import com.example.weather.network.Status
import com.example.weather.network.StatusEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ForecastWeatherRepository {
    private val forecastWeatherMutableLiveData = MutableLiveData<ForecastWeatherResponse>()
    private val statusMutableLiveData = MutableLiveData<StatusEvent>()

    val forecastWeatherLiveData: LiveData<ForecastWeatherResponse> = forecastWeatherMutableLiveData
    val statusLiveData: LiveData<StatusEvent> = statusMutableLiveData

    fun fetchForecastWeather() {
        statusMutableLiveData.postValue(StatusEvent(Status.LOADING))
        val location: Location? = LocationHelper.getLastKnownLocation(MyApplication.getContext())
        if (location == null) {
            statusMutableLiveData.postValue(
                StatusEvent(
                    Status.ERROR,
                    MyApplication.getContext().getString(R.string.enable_location_services)
                )
            )
            if (!LocationHelper.isLocationPermissionGranted(MyApplication.getContext()))
                statusMutableLiveData.postValue(
                    StatusEvent(
                        Status.ERROR,
                        MyApplication.getContext().getString(R.string.text_location_permission)
                    )
                )
        } else
            ApiClient.apiService.fetchForecastWeather(
                location.latitude,
                location.longitude,
                MySharedPreferences.getUnits()
            ).enqueue(
                object : Callback<ForecastWeatherResponse> {
                    override fun onFailure(call: Call<ForecastWeatherResponse>, t: Throwable) {
                        statusMutableLiveData.postValue(
                            StatusEvent(
                                Status.ERROR,
                                "Some Error Occoured!"
                            )
                        )
                    }

                    override fun onResponse(
                        call: Call<ForecastWeatherResponse>,
                        response: Response<ForecastWeatherResponse>
                    ) {
                        if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                            statusMutableLiveData.postValue(
                                StatusEvent(
                                    Status.SUCCESS,
                                    "Data Loaded Successfully!"
                                )
                            )
                            forecastWeatherMutableLiveData.postValue(response.body())
                        }
                    }
                })
    }
}