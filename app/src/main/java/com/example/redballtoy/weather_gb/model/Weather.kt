package com.example.redballtoy.weather_gb.model

//A class describing the state of the weather in a particular city
data class Weather(
        val city: City = getDefaultCity(),
        val currentTemperature: Int = 0,
        val feelsLikeTemperature: Int = 0


)

fun getDefaultCity() = City(
        "Москва",
        55.755826,
        37.6172990000035
)
