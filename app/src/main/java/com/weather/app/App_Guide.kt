package com.weather.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2

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