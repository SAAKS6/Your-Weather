package com.weather.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class API_Loader : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_api_loader)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val binaryFile = BinaryFile()
        var userData: UserData? = binaryFile.getData(this)

        for(x in 1..60){
            progressBar.progress = x
            Thread.sleep(25)
        }

        if(userData != null){

            val api = API(this)
            var apiURL = "https://api.openweathermap.org/data/2.5/forecast?q="

            val city: String?
            val country: String?

            if(userData.getIsSearching()){

                city = intent.getStringExtra("city")
                country = intent.getStringExtra("country")

                apiURL += "$city,$country"

                userData.setIsSearching(false)

                if(!binaryFile.setData(userData, this)){
                    Toast.makeText(this, "Try Clearing Saved Data from App Settings", Toast.LENGTH_LONG).show()
                }

            }else{
                city = userData.getCity()
                country = userData.getCountry()
                apiURL += "$city,$country"
            }

            apiURL += "&appid=5a7d4d78d843ea41756e658ffc3fc2a7&units="

            apiURL += if(userData.getTempUnit() == 'C'){
                "metric"
            }else{
                "imperial"
            }
            Log.d("DATA", apiURL)
            api.getWeatherData(apiURL,
                { ApiJson ->
                    // Handle the JSON response her

                    for(x in 61..100){
                        progressBar.progress = x
                    }

                    showWeather(ApiJson, userData!!.getTempUnit(), city, country)
                },
                { Error ->
                    // Handle error

                    if(Error != 0){
                        intent = Intent(this, MainActivity::class.java)

                        if (Error == 404) {
                            Toast.makeText(this, "Invalid Country or City", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(this, "NetWork Error", Toast.LENGTH_SHORT).show()
                        }

                        userData = null
                        binaryFile.setData(userData, this)

                        startActivity(intent)
                        finish()
                    }else{

                        progressBar.progress = 100
                        progressBar.visibility = View.GONE

                        val msgText: TextView = findViewById(R.id.msgText)
                        val tryAgain: Button = findViewById(R.id.tryAgain_Button)

                        val size = 14

                        msgText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size.toFloat())
                        msgText.text = getString(R.string.noInternet)

                        tryAgain.visibility = View.VISIBLE

                        tryAgain.setOnClickListener {
                            recreate()
                        }
                    }
                })
        }
    }
    private fun showWeather(Apiresponse: JSONObject, tempUnit: Char, city: String?, country: String?){
        val intent = Intent(this, WeatherActivity::class.java)

        intent.putExtra("ApiJsonString", Apiresponse.toString())
        intent.putExtra("tempUnit", tempUnit)
        intent.putExtra("city", city)
        intent.putExtra("country", country)

        startActivity(intent)
        finish()
    }
}