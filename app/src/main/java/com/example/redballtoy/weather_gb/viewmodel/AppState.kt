package com.example.redballtoy.weather_gb.viewmodel

import com.example.redballtoy.weather_gb.model.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
