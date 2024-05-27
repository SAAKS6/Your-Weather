package com.weather.app.WeatherData

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Root(

    @SerializedName("cod") var cod : String,
    @SerializedName("message") var message : Int,
    @SerializedName("cnt") var cnt : Int,
    @SerializedName("list") var list : kotlin.collections.List<List>,
    @SerializedName("city") var city : City

): Serializable