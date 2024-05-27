package com.weather.app.WeatherData

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import kotlin.collections.List

data class List(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Long,
    val pop: Double,
    val sys: Sys,
    val dt_txt: String,
    var index: Int
): Serializable