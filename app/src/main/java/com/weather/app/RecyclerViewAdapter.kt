package com.weather.app

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.weather.app.WeatherData.List

class RecyclerViewAdapter(
    private val itemList: kotlin.collections.List<List>,
    private val tempUnit: Char,
    private val popupDialog: PopUp_Dialog,
    private val city: String?,
    private val country: String?
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun getItemCount(): Int {

        return itemList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.weather_list, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load("https://openweathermap.org/img/wn/" + itemList[position].weather[0].icon + "@2x.png")
            .into(holder.weatherIconImageView)

        if (tempUnit == 'C'){
            holder.currentTemp.text = itemList[position].main.temp.toString() + "°C"
        }else{
            holder.currentTemp.text = itemList[position].main.temp.toString() + "°F"
        }

        holder.description.text = itemList[position].weather[0].main

        holder.item.setOnClickListener{ popupDialog.showDialog(itemList[position], city, country) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var weatherIconImageView: ImageView
        var currentTemp: TextView
        var description: TextView
        val item: LinearLayout
        init {
            weatherIconImageView = itemView.findViewById(R.id.weatherIcon_ImageView)
            currentTemp = itemView.findViewById(R.id.currentTemp_TextView)
            description = itemView.findViewById(R.id.description_TextView)
            item = itemView.findViewById(R.id.item)
        }
    }
}
