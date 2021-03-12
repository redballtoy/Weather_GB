package com.example.redballtoy.weather_gb.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.redballtoy.weather_gb.R
import com.example.redballtoy.weather_gb.databinding.MainCityListFragmentBinding
import com.example.redballtoy.weather_gb.model.Weather
import com.example.redballtoy.weather_gb.model.getRussianCities
import com.example.redballtoy.weather_gb.view.detail.DetailsFragment
import com.example.redballtoy.weather_gb.viewmodel.AppState
import com.example.redballtoy.weather_gb.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(),
        OnItemClickListener {

    private var _binding: MainCityListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterRecycleForCitiesList: MainFragmentRecyclerAdapter
    private lateinit var viewModel: MainViewModel
    private var isDataSetRus = true


    private val weather: List<Weather> = getRussianCities()

    //add class layout by binding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = MainCityListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //get Views from the binding class
    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {

        }



        val cityList = binding.rvCities
        cityList.layoutManager = LinearLayoutManager(view.context)
        adapterRecycleForCitiesList = MainFragmentRecyclerAdapter(this)
        cityList.adapter = adapterRecycleForCitiesList
        binding.fbChangeRegion.setOnClickListener { changeWeatherDataSet() }
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveDate().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSourceRus()
    }


    //the layout destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //fragment may be destroyed
    override fun onDestroy() {
        adapterRecycleForCitiesList.removeListener()
        super.onDestroy()
    }

    //passing the parameters of the selected city to the detail fragment
    override fun onItemViewClick(weather: Weather) {
        val manager = activity?.supportFragmentManager
        if (manager != null) {
            val bundle = Bundle()
            bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA, weather)
            manager.beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
        }
        Toast.makeText(context, "City: ${weather.city.cityName}", Toast.LENGTH_SHORT).show()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.pbMainFragmentLoading.visibility = View.INVISIBLE
                adapterRecycleForCitiesList.setWeather(appState.weatherData)
            }
            is AppState.Loading -> {
                binding.pbMainFragmentLoading.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.pbMainFragmentLoading.visibility = View.INVISIBLE
                Snackbar.make(
                        binding.fbChangeRegion, "Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.reload) { viewModel.getWeatherFromLocalSourceRus() }
                        .show()
            }
        }
    }

    //change City set by click on FAB
    private fun changeWeatherDataSet() {
        if (!isDataSetRus) {
            viewModel.getWeatherFromLocalSourceRus()
            binding.fbChangeRegion.setImageResource(R.drawable.ic_russia)
        } else {
            viewModel.getWeatherFromLocalSourceWorld()
            binding.fbChangeRegion.setImageResource(R.drawable.ic_eng)
        }
        isDataSetRus = !isDataSetRus
    }

    companion object{
        fun newInstance()=MainFragment()
    }



}
