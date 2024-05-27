package com.weather.app.WeatherData

import java.io.Serializable

data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Long,
    val sea_level: Long,
    val grnd_level: Long,
    val humidity: Int,
    val temp_kf: Double,
): Serializable