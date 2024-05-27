package com.weather.app

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)

        if(sharedPreferences.getBoolean("firstLaunch", true)){
            startActivity(Intent(this, App_Guide::class.java))
            finish()
        }else{
            val binaryFIle = BinaryFile()
            var userData: UserData? = binaryFIle.getData(this)

            val permissionHandler = PermissionHandler()

            if(!permissionHandler.checkPermission(this)){
                val intent = Intent(this, Permission::class.java)
                startActivity(intent)
                finish()
            }

            if(userData != null && permissionHandler.checkPermission(this)){
                showWeather()
            }else if(permissionHandler.checkPermission(this)){

                val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

                //EditTexts
                val country: EditText = findViewById(R.id.country_EditText)
                val city: EditText = findViewById(R.id.city_EditText)

                permissionHandler.getLocation(this, fusedLocationProviderClient, country, city)

                //Button
                val submit: Button = findViewById(R.id.submit_Button)
                val celsius: Button = findViewById(R.id.celsius_Button)
                val fahrenheit: Button = findViewById(R.id.fahrenheit_Button)

                var tempUnit = ' ';

                val typedValue = TypedValue()

                country.setOnClickListener{
                    this.theme.resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true)
                    country.setHintTextColor(typedValue.data)
                }

                city.setOnClickListener{
                    this.theme.resolveAttribute(androidx.appcompat.R.attr.colorAccent, typedValue, true)
                    city.setHintTextColor(typedValue.data)
                }

                celsius.setOnClickListener{

                    if(tempUnit == 'F' || tempUnit == ' '){
                        this.theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
                        celsius.setTextColor(typedValue.data)

                        celsius.background = AppCompatResources.getDrawable(this, R.drawable.button_bg)

                        this.theme.resolveAttribute(androidx.appcompat.R.attr.color, typedValue, true)
                        fahrenheit.setTextColor(typedValue.data)

                        fahrenheit.background = AppCompatResources.getDrawable(this, R.drawable.edittext_bg)

                        tempUnit = 'C'
                    }
                }

                fahrenheit.setOnClickListener{

                    if(tempUnit == 'C' || tempUnit == ' '){
                        this.theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
                        fahrenheit.setTextColor(typedValue.data)

                        fahrenheit.background = AppCompatResources.getDrawable(this, R.drawable.button_bg)

                        this.theme.resolveAttribute(androidx.appcompat.R.attr.color, typedValue, true)
                        celsius.setTextColor(typedValue.data)

                        celsius.background = AppCompatResources.getDrawable(this, R.drawable.edittext_bg)

                        tempUnit = 'F'
                    }
                }

                //Submit Button Click Listener
                submit.setOnClickListener{
                    if(!country.text.isEmpty()){
                        if(!city.text.isEmpty()){
                            if(tempUnit != ' '){

                                // Initialize Class Object
                                userData = UserData(country.text.toString().trim(), city.text.toString().trim(), tempUnit)

                                binaryFIle.setData(userData, this) // Save Class Object into Binary File
                                showWeather()

                            }else{
                                Toast.makeText(this, "Select your desired Temperature Unit", Toast.LENGTH_LONG).show()
                                celsius.setTextColor(getColor(R.color.red))
                                fahrenheit.setTextColor(getColor(R.color.red))
                            }
                        }else{
                            Toast.makeText(this, "Enter your City", Toast.LENGTH_SHORT).show()
                            city.setHintTextColor(getColor(R.color.red))
                        }
                    }else{
                        Toast.makeText(this, "Enter your Country", Toast.LENGTH_SHORT).show()
                        country.setHintTextColor(getColor(R.color.red))
                    }
                }
            }
        }
    }

    private fun showWeather(){
        val intent = Intent(this, API_Loader::class.java)
        startActivity(intent)
        finish()
    }
}

// https://api.openweathermap.org/data/2.5/weather?lat=33.6995&lon=73.0363&appid=5a7d4d78d843ea41756e658ffc3fc2a7&units=metric
// https://api.openweathermap.org/data/2.5/forecast?lat=33.6995&lon=73.0363&appid=5a7d4d78d843ea41756e658ffc3fc2a7&units=metric

// https://api.openweathermap.org/data/2.5/forecast?q=city,country&appid=5a7d4d78d843ea41756e658ffc3fc2a7&units=metric

// Example
// https://api.openweathermap.org/data/2.5/forecast?q=islamabad,pakistan&appid=5a7d4d78d843ea41756e658ffc3fc2a7&units=metric

//https://openweathermap.org/img/wn/01d@2x.png