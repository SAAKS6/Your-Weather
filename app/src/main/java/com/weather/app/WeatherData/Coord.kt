package com.weather.app.WeatherData

import com.google.gson.annotations.SerializedName

data class Coord(

    @SerializedName("lat") var lat : Double,
    @SerializedName("lon") var lon : Double

)