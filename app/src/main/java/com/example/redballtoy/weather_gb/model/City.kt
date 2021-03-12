package com.example.redballtoy.weather_gb.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
        val cityName: String,
        val latCity: Double,
        val lonCity: Double
): Parcelable
