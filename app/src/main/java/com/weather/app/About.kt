package com.weather.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class About : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.about_ScrollView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.open_weather_ImageView).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://openweathermap.org")))
        }

        findViewById<ImageView>(R.id.android_studio_ImageView).setOnClickListener {
            startActivity(Intent( Intent.ACTION_VIEW, Uri.parse("https://developers.android.com")))
        }

        findViewById<ImageView>(R.id.kotlin_ImageView).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://kotlinlang.org")))
        }

        findViewById<ImageView>(R.id.mongodb_ImageView).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mongodb.com/products/platform/cloud")))
        }
    }
}