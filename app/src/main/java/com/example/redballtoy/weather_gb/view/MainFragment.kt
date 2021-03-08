package com.example.redballtoy.weather_gb.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.redballtoy.weather_gb.databinding.MainFragmentBinding
import com.example.redballtoy.weather_gb.model.Weather
import com.example.redballtoy.weather_gb.viewmodel.AppState
import com.example.redballtoy.weather_gb.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar


class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null //может возвращать нулевое значение
    private val binding get() = _binding!! //мы устанавливае что не должен возвращать нулевое значение
    private lateinit var viewModel: MainViewModel


    //using a binding to access the Views of a Fragment
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*ViewModel создается с использованием ViewModelProvider,
        owner this - означает что жизненный цикл ViewModel будет соответствовать жизненному
        циклу фрагмента, при удаленнии фрагмента, ViewModel так же будет удалена
        get определяет какая ViewModel будет использоваться*/
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        /*subscribing to the created ViewModel
        viewLifecycleOwner subscription based on the state of the object
        если View в состоянии onPause то ничего делвть не надо
        Observer { renderData(it) - объект который будет принимать новое состояние
        it - новое состояние нашего приложения*/
        viewModel.getLiveDate().observe(
                //это жизненный цикл вьюшки, поскольку ее состояние может меняться а фрагмент
                //оставаться тем хе самым
                viewLifecycleOwner,
                //при изменении сояния объекта он будет про разному отображаться
                Observer { state: AppState -> renderData(state) })
        //определяем из какого локального хранилища будеи получать данные
        viewModel.getWeatherFromLocalSource()

    }


    //функция для отображения информации при изменении состояния
    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                val weatherData = appState.weatherData
                binding.flLoading.visibility=View.GONE
                setData(weatherData)
            }
            is AppState.Loading -> {
                binding.flLoading.visibility=View.VISIBLE
            }
            is AppState.Error -> {
                binding.flLoading.visibility=View.GONE
                Snackbar
                        .make(binding.flLoading, "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
                        .show()
            }
        }
    }

    //отображает погоду если данные пришли SUCESS
    private fun setData(weatherData: Weather) {
        binding.tvCityName.text = weatherData.city.city
        val ltLat = "lt/ln: " +
                "${weatherData.city.lat}" +
                "/${weatherData.city.lon}"
        binding.tvLatitudeValue.text = ltLat
        binding.tvCurrentTemperatureValue.text=weatherData.currentTemperature
        binding.tvFeelAsTemperatureValue.text=weatherData.feelsLikeTemperature
    }

    override fun onDestroy() {
        super.onDestroy()
        //cleaning binding
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}