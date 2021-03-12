package com.example.redballtoy.weather_gb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redballtoy.weather_gb.model.Repository
import com.example.redballtoy.weather_gb.model.RepositoryImpl
import java.lang.Thread.sleep


class MainViewModel(
        private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
        private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    //LivaData to which we will subscribe (on AppState object object which is in LivaData)
    //get notified when AppState object changes
    fun getLiveDate(): LiveData<AppState> = liveDataToObserve


    //get weather information from three different sources
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian=true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian=false)
    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian=true)

    //LiveData gets data from source
    private fun getDataFromLocalSource(isRussian: Boolean) {
        //putting data into liveDataToObserve in the current thread
        //and the subscriber will receive it in the same thread*/
        liveDataToObserve.value = AppState.Loading

        //simulate a request to the network
        Thread {
            sleep(1000)
            //puts data and returns it to the main thread
            val data =if(isRussian) repositoryImpl.getWeatherFromLocalStorageRus()
            else repositoryImpl.getWeatherFromLocalStorageWorld()
            liveDataToObserve.postValue(
                    AppState.Success(data)
            )
        }.start()
    }
}

