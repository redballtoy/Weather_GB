package com.example.redballtoy.weather_gb.model



//The repository implements how the data will be received
class RepositoryImpl:Repository {
    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
       return getWorldCities()
    }


}