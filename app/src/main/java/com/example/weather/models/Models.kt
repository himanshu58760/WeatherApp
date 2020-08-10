package com.example.weather.models

data class CurrentWeatherResponse(val current: Current, val hourly: List<Hourly>)

data class ForecastWeatherResponse(val daily: List<Daily>)

data class Current(
    val feels_like: Double, // what temperature is felt,accounts for human perception of weather
    val humidity: Int,
    val temp: Double,   // temperature, units depend on the request
    val visibility: Int, // average visibility in metres
    val weather: List<Weather>,
    val wind_speed: Double // units default m/s,metric m/s, imperial miles/hr
)

data class Hourly(
    val dt: Long, // time of recording in unix utc seconds
    val temp: Double,
    val weather: List<Weather>
)

data class Daily(
    val dt: Long,        // time of the forcasted data
    val temp: Temp,
    val weather: List<Weather>
)

data class Weather(
    val icon: String,   //weather icon code
    val main: String    //weather type
)

data class Temp(
    val max: Double,
    val min: Double
)