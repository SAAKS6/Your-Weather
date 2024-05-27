package com.weather.app.WeatherData

import java.io.Serializable

data class Wind(
    val speed: Float,
    val deg: Long,
    val gust: Double,
): Serializable