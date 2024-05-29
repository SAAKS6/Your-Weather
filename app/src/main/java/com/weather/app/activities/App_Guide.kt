package com.weather.app.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.weather.app.R
import com.weather.app.adapters.FragmentAdapter

class App_Guide : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_app_guide)

        findViewById<ViewPager2>(R.id.guide_ViewPager).adapter = FragmentAdapter(this)
    }

    fun launchApp(){
        val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("firstLaunch", false).apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}