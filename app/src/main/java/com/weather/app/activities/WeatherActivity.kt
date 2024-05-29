package com.weather.app.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.weather.app.R
import com.weather.app.WeatherData.Root
import com.weather.app.system.API
import com.weather.app.system.BinaryFile
import com.weather.app.user.UserData
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.ext.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.mongodb.kbson.BsonArray
import org.mongodb.kbson.BsonDocument


class WeatherActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather)

        val apiJsonString: String? = intent.getStringExtra("ApiJsonString")
        val tempUnit: Char = intent.getCharExtra("tempUnit", ' ')
        val cityText: String? = intent.getStringExtra("city")
        val countryText: String? = intent.getStringExtra("country")

        val api = API(this)
        val root: Root? = api.parseWeatherData(apiJsonString)

        if (root != null) {

            val weatherIcon_ImageView: ImageView = findViewById(R.id.weatherIcon_ImageView)
            val currentTemp: TextView = findViewById(R.id.currentTemp_TextView)
            val description: TextView = findViewById(R.id.description_TextView)
            val feelsLike: TextView = findViewById(R.id.feels_like_TextView)
            val humidity: TextView = findViewById(R.id.humidity_TextView)
            val windSpeed: TextView = findViewById(R.id.wind_speed_TextView)
            val time: TextView = findViewById(R.id.time_TextView)
            val city: TextView = findViewById(R.id.city_TextView)
            val date: TextView = findViewById(R.id.date_TextView)
            val country: TextView = findViewById(R.id.country_TextView)

            val weatherIcon_ImageView2: ImageView = findViewById(R.id.weatherIcon_ImageView2)
            val currentTemp2: TextView = findViewById(R.id.currentTemp_TextView2)
            val description2: TextView = findViewById(R.id.description_TextView2)
            val humidity2: TextView = findViewById(R.id.humidity_TextView2)

            val time2: TextView = findViewById(R.id.time_TextView2)
            val date2: TextView = findViewById(R.id.date_TextView2)

            val searchBar: AutoCompleteTextView = findViewById(R.id.searchBar_AutoCompleteTextView)

            if(tempUnit == 'C'){
                currentTemp.text = root.list[0].main.temp.toString() + "°C"
                currentTemp2.text = root.list[0].main.temp.toString() + "°C"
            }else if(tempUnit == 'F'){
                currentTemp.text = root.list[0].main.temp.toString() + "°F"
                currentTemp2.text = root.list[0].main.temp.toString() + "°F"
            }

            var dt_text: String = root.list[0].dt_txt

            var imageURL = "https://openweathermap.org/img/wn/" + root.list[0].weather[0].icon + "@4x.png"
            Picasso.get().load(imageURL).into(weatherIcon_ImageView)

            description.text = root.list[0].weather[0].main
            feelsLike.text = "Feels Like: " + root.list[0].main.feels_like.toString() + "°C"
            humidity.text = "Humidity: " + root.list[0].main.humidity.toString()
            windSpeed.text = "Wind Speed: " + root.list[0].wind.speed.toString()
            time.text = "Time: " + dt_text.substring(11, 16)
            date.text = "Date: " + dt_text.substring(8, 10) + " " + getMonth(dt_text.substring(5, 7)) + " " + dt_text.substring(0, 4)
            city.text = "$cityText,"
            country.text = countryText

            // Next 3h Forecast

            imageURL = "https://openweathermap.org/img/wn/" + root.list[1].weather[0].icon + "@2x.png"
            Picasso.get().load(imageURL).into(weatherIcon_ImageView2)

            dt_text = root.list[1].dt_txt

            description2.text = root.list[1].weather[0].main
            humidity2.text = "Humidity: " + root.list[1].main.humidity.toString()
            time2.text = "Time: " + dt_text.substring(11, 16)
            date2.text = "Date: " + dt_text.substring(8, 10) + " " + getMonth(dt_text.substring(5, 7)) + " " + dt_text.substring(0, 4)

            findViewById<Button>(R.id.more_forecast_Button)
                .setOnClickListener { moreForecast(apiJsonString, tempUnit, cityText, countryText) }


            findViewById<ImageView>(R.id.home_ImageView).setOnClickListener {
                startActivity(Intent(this, API_Loader::class.java))
                finish()
            }

            findViewById<ImageView>(R.id.settings_ImageView).setOnClickListener {
                val intent = Intent(this, Settings::class.java)
                intent.putExtra("tempUnit", tempUnit)
                startActivity(intent)
            }

            searchBar.setOnItemClickListener{ adapterView, view, i, l ->

                val binaryFile = BinaryFile()
                val userData: UserData? = binaryFile.getData(this)

                userData!!.setIsSearching(true)

                if(binaryFile.setData(userData, this)){
                    val separatedData = adapterView.getItemAtPosition(i).toString().split(", ")
                    val intent = Intent(this, API_Loader::class.java)
                    intent.putExtra("city", separatedData[0])
                    intent.putExtra("country", separatedData[1])
                    startActivity(intent)
                    finish()
                }
            }

            searchBar.hint = "Fetching Data..."
            CoroutineScope(Dispatchers.Main).launch { getCountries() }
        }
    }

    private fun moreForecast(ApiJsonString: String?, tempUnit: Char, city: String?, country: String?){
        val intent = Intent(this, ForecastedWeatherList::class.java)

        intent.putExtra("ApiJsonString", ApiJsonString)
        intent.putExtra("tempUnit", tempUnit)
        intent.putExtra("city", city)
        intent.putExtra("country", country)

        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId){

            R.id.addCity_menu_item -> {
                addCity()
                true
            }

            R.id.browseCity_menu_item -> {

                browseCity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun browseCity(){

    }

    private fun addCity() {
        intent = Intent(this, MainActivity::class.java)

        intent.putExtra("addCity", true)
        startActivity(intent)
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

    private fun getCountries(){
        val app = App.create("weatherapp-jwpwhub") // Passes argument is App ID

        val context = this

        runBlocking {
            val user = app.login(Credentials.anonymous())

            if(app.currentUser != null){

                val bsonArray = user.functions.call<BsonArray>("getData")

                val mutableList = mutableListOf<String>()

                bsonArray.forEach {
                    if(it is BsonDocument){
                        mutableList.add((it.getString("city").value + ", " + it.getString("country").value))
                    }
                }

                val adapter = ArrayAdapter(context, R.layout.auto_textview_style, mutableList)
                val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.searchBar_AutoCompleteTextView)
                autoCompleteTextView.setAdapter(adapter)
                autoCompleteTextView.hint = getString(R.string.search)

                findViewById<Button>(R.id.more_forecast_Button).text = getString(R.string.more_forecast)
            }
        }
    }
}