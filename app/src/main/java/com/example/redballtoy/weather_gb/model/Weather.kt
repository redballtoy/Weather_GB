package com.example.redballtoy.weather_gb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//A class describing the state of the weather in a particular city
@Parcelize
data class Weather(
        val city: City = getDefaultCity(),
        val currentTemperature: Int = 12,
        val feelsLikeTemperature: Int = 15
) : Parcelable

   fun getRussianCities(): List<Weather> {
        return listOf(
                Weather(City("Mocква", 22.0, 22.0), 12, 15),
                Weather(City("Бибирево", 22.0, 22.0), 12, 15),
                Weather(City("Ярославль", 22.0, 22.0), 12, 15),
                Weather(City("Лахость", 22.0, 22.0), 12, 15),
                Weather(City("Муравейка", 22.0, 22.0), 12, 15),

                )
    }

    fun getWorldCities(): List<Weather> {
        return listOf(
                Weather(City("Moscow", 22.0, 22.0), 12, 15),
                Weather(City("Bibirevo", 22.0, 22.0), 12, 15),
                Weather(City("Yaroslavl", 22.0, 22.0), 12, 15),
                Weather(City("Lachost", 22.0, 22.0), 12, 15),
                Weather(City("Muraveyka", 22.0, 22.0), 12, 15),

                )
    }

fun getDefaultCity() = City(
        "Москва",
        55.755826,
        37.6172990000035
)
