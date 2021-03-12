package com.example.redballtoy.weather_gb.model

interface Repository {
    fun getWeatherFromServer():Weather
    fun getWeatherFromLocalStorageRus():List<Weather>
    fun getWeatherFromLocalStorageWorld():List<Weather>
}