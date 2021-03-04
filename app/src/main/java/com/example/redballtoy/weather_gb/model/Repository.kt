package com.example.redballtoy.weather_gb.model

interface Repository {
    fun getWeatherFromServer():Weather
    fun getWeatherFromLocalStorage():Weather
}