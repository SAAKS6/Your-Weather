package com.weather.app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.squareup.picasso.Picasso
import com.weather.app.WeatherData.List

class PopUp_Dialog (private val context: Context, private val tempUnit: Char) {

    @SuppressLint("SetTextI18n", "InflateParams")
    fun showDialog(list: List, cityText: String?, countryText: String?){

        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pop_up_dialog, null)

        val weatherIcon_ImageView: ImageView = view.findViewById(R.id.weatherIcon_ImageView)
        val currentTemp: TextView = view.findViewById(R.id.currentTemp_TextView)
        val description: TextView = view.findViewById(R.id.description_TextView)
        val feelsLike: TextView = view.findViewById(R.id.feels_like_TextView)
        val humidity: TextView = view.findViewById(R.id.humidity_TextView)
        val windSpeed: TextView = view.findViewById(R.id.wind_speed_TextView)
        val time: TextView = view.findViewById(R.id.time_TextView)
        val city: TextView = view.findViewById(R.id.city_TextView)
        val date: TextView = view.findViewById(R.id.date_TextView)
        val country: TextView = view.findViewById(R.id.country_TextView)

        val close: Button = view.findViewById(R.id.close_Button)

        if(tempUnit == 'C'){
            currentTemp.text = list.main.temp.toString() + "°C"
        }else{
            currentTemp.text = list.main.temp.toString() + "°F"
        }

        var dt_text: String = list.dt_txt

        var imageURL = "https://openweathermap.org/img/wn/" + list.weather[0].icon + "@4x.png"
        Picasso.get().load(imageURL).into(weatherIcon_ImageView)

        description.text = list.weather[0].main
        feelsLike.text = "Feels Like: " + list.main.feels_like.toString() + "°C"
        humidity.text = "Humidity: " + list.main.humidity.toString()
        windSpeed.text = "Wind Speed: " + list.wind.speed.toString()
        time.text = "Time: " + dt_text.substring(11, 16)
        date.text = "Date: " + dt_text.substring(8, 10) + " " + getMonth(dt_text.substring(5, 7)) + " " + dt_text.substring(0, 4)
        city.text = "$cityText,"
        country.text = countryText

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        close.setOnClickListener { dialog.dismiss() }
    }

    private fun getMonth(month: String): String {
        return when (month) {
            "01" -> "Jan"
            "02" -> "Feb"
            "03" -> "Mar"
            "04" -> "Apr"
            "05" -> "May"
            "06" -> "Jun"
            "07" -> "Jul"
            "08" -> "Aug"
            "09" -> "Sep"
            "10" -> "Oct"
            "11" -> "Nov"
            "12" -> "Dec"
            else -> ""
        }
    }
}