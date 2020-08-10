package com.example.weather.repository

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.nasaphotooftheday.network.ApiClient
import com.example.news.Helper.MyApplication
import com.example.news.Helper.MySharedPreferences
import com.example.weather.R
import com.example.weather.helper.LocationHelper
import com.example.weather.models.CurrentWeatherResponse
import com.example.weather.network.Status
import com.example.weather.network.StatusEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CurrentWeatherRepository {
    private val currentWeatherMutableLiveData = MutableLiveData<CurrentWeatherResponse>()
    private val statusMutableLiveData = MutableLiveData<StatusEvent>()

    val currentWeatherLiveData: LiveData<CurrentWeatherResponse> = currentWeatherMutableLiveData
    val statusLiveData: LiveData<StatusEvent> = statusMutableLiveData

    fun fetchCurrentWeather() {
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
            ApiClient.apiService.fetchCurrentWeather(
                location.latitude,
                location.longitude,
                MySharedPreferences.getUnits()
            )
                .enqueue(
                    object : Callback<CurrentWeatherResponse> {
                        override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                            statusMutableLiveData.postValue(
                                StatusEvent(
                                    Status.ERROR,
                                    MyApplication.getContext()
                                        .getString(R.string.some_error_occoured)
                                )
                            )
                        }

                        override fun onResponse(
                            call: Call<CurrentWeatherResponse>,
                            response: Response<CurrentWeatherResponse>
                        ) {
                            if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                                statusMutableLiveData.postValue(StatusEvent(Status.SUCCESS))
                                currentWeatherMutableLiveData.postValue(response.body())
                            }
                        }
                    })
    }
}