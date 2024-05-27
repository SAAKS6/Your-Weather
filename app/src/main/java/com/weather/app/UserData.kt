package com.weather.app

import java.io.Serializable

class UserData(private val country: String, private val city: String, private var tempUnit: Char):Serializable {

    private var isSearching: Boolean = false

    fun getCountry(): String {
        return this.country
    }

    fun getCity(): String {
        return this.city
    }

    fun getTempUnit(): Char {
        return this.tempUnit
    }

    fun setTempUnit(tempUnit: Char) {
        this.tempUnit = tempUnit
    }

    fun getIsSearching(): Boolean {
        return this.isSearching
    }

    fun setIsSearching(isSearching: Boolean) {
        this.isSearching = isSearching
    }
}
