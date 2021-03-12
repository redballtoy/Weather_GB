package com.example.redballtoy.weather_gb.view.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.redballtoy.weather_gb.databinding.DetailWeatherFragmentBinding
import com.example.redballtoy.weather_gb.model.Weather


class DetailsFragment : Fragment() {
    private var _binding: DetailWeatherFragmentBinding? = null //может возвращать нулевое значение
    private val binding get() = _binding!! //мы устанавливае что не должен возвращать нулевое значение

    //using a binding to access the Views of a Fragment
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = DetailWeatherFragmentBinding.inflate(inflater, container, false)
        return _binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weather = arguments?.getParcelable<Weather>(BUNDLE_EXTRA)
        if(weather != null){

            val city = weather.city
            binding.tvDetailCityName.text = city.cityName
            binding.tvDetailCityLatitudeValue.text =String.format(
                    "lt/ln: %s, %s",
                    city.latCity.toString(),
                    city.lonCity.toString()
            )
            binding.tvDetailCityCurrentTemperatureValue.text =weather.currentTemperature.toString()
            binding.tvDetailCityFeelAsTemperatureValue.text =weather.feelsLikeTemperature.toString()
            binding.pgLoading.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }


    companion object {

        const val BUNDLE_EXTRA = "weather"

        fun newInstance(bundle: Bundle):DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments=bundle
            return fragment
        }
    }
}