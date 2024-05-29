package com.weather.app.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.weather.app.R
import com.weather.app.system.BinaryFile
import com.weather.app.user.UserData

class Settings : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)


        var flag = false

        val reset:Button = findViewById(R.id.reset_Button)
        val tempUnit: ToggleButton = findViewById(R.id.tempUnit_ToggleButton)

        val tempUnitData: Char = intent.getCharExtra("tempUnit", ' ')

        val binaryFile = BinaryFile()
        var userData: UserData? = binaryFile.getData(this)

        tempUnit.textOn = "Celsius"
        tempUnit.textOff = "Fahrenheit"

        if(tempUnitData == 'C'){
            tempUnit.isChecked = true
        }
        else if(tempUnitData == 'F'){
            tempUnit.isChecked = false
        }

        tempUnit.setOnClickListener {

            if(tempUnit.isChecked){
                Toast.makeText(this, "Restart App to switch to Celsius", Toast.LENGTH_LONG).show()
                userData!!.setTempUnit('C')
            }else{
                Toast.makeText(this, "Restart App to switch to Fahrenheit", Toast.LENGTH_LONG).show()
                userData!!.setTempUnit('F')
            }

            binaryFile.setData(userData, this)
        }

        reset.setOnClickListener {
            if(!flag){
                reset.text = "YES, DELETE"
                flag = true
            }else{
                userData = null

                if(binaryFile.setData(userData, this)){
                    Toast.makeText(this, "Data Cleared", Toast.LENGTH_LONG).show()

                    val sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("firstLaunch", true).apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }

        findViewById<Button>(R.id.about_Button).setOnClickListener {
            val intent = Intent(this, About::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.feedback_Button).setOnClickListener {

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:asfandyarnaeem81@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "User Feedback")
            intent.putExtra(Intent.EXTRA_TEXT, "--- Start from below this line ---")

            startActivity(intent)
        }
    }
}