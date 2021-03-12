package com.example.redballtoy.weather_gb.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.redballtoy.weather_gb.R
import com.example.redballtoy.weather_gb.model.Weather


//RecyclerView.Adapter is generic and we can specify our own type WH (class Adapter<VH extends ViewHolder>)
class MainFragmentRecyclerAdapter(
    private var itemClickListener: OnItemClickListener?
) : RecyclerView.Adapter<MainFragmentRecyclerAdapter.CityHolder>() {

    //initialization just to not make weather nullable
    private var weather: List<Weather> = listOf()

    //create view from viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityHolder(view)
    }

    //binding ListWeather with RecyclerView position
    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        val weatherListPositionValue = weather[position]
        holder.bind(weatherListPositionValue, itemClickListener)
    }

    override fun getItemCount(): Int {
        return weather.size
    }

    //we do not use mutable list because there is no task in the adapter to change the parameters of the list,
    // this makes sure that the given container with the collection will not be changed by the adapter
    fun setWeather(data: List<Weather>){
        weather=data
        notifyDataSetChanged()
    }

    fun removeListener(){
        itemClickListener=null
    }

    class CityHolder(
            var itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val cityNameList = itemView.findViewById<TextView>(R.id.tvCityNameList)

        fun bind(weather: Weather, clickListener: OnItemClickListener?) {
            cityNameList.text = weather.city.cityName
            itemView.setOnClickListener {
                clickListener?.onItemViewClick(weather)
            }
        }
    }
}
