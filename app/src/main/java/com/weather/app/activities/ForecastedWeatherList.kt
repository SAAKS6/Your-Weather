package com.weather.app.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weather.app.system.PopUp_Dialog
import com.weather.app.R
import com.weather.app.WeatherData.Root
import com.weather.app.adapters.RecyclerViewAdapter
import com.weather.app.system.API

class ForecastedWeatherList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forecasted_weather_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val apiJsonString: String? = intent.getStringExtra("ApiJsonString")
        val tempUnit: Char = intent.getCharExtra("tempUnit", ' ')
        val cityText: String? = intent.getStringExtra("city")
        val countryText: String? = intent.getStringExtra("country")

        val api = API(this)
        val root: Root? = api.parseWeatherData(apiJsonString)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val popUpDialog = PopUp_Dialog(this, tempUnit)

        val screenOrientation = resources.configuration.orientation

        if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(GridLayoutManager(this, 4))
        } else {
            recyclerView.setLayoutManager(GridLayoutManager(this, 2))
        }

        val adapter = RecyclerViewAdapter(root!!.list, tempUnit, popUpDialog, cityText, countryText)
        recyclerView.setAdapter(adapter)

    }
}