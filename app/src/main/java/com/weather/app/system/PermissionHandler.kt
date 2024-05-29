package com.weather.app.system

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.Locale

class PermissionHandler : AppCompatActivity() {

    private val REQUEST_LOCATION_PERMISSION = 123
    fun checkPermission(context: Context): Boolean{

        if(checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true
        }else{ return false }
    }

    fun askPermission(activity: Activity){

        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    @SuppressLint("NewApi")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("NewApi")
    fun getLocation(context: Context, fusedLocationClient: FusedLocationProviderClient, country: EditText, city: EditText) {

        if(checkPermission(context)){

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location!= null) {
                        val latitude = location.latitude
                        val longitude = location.longitude

                        val geocoder = Geocoder(context, Locale.getDefault())
                        val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                        if (!addresses.isNullOrEmpty()) {
                            val address = addresses[0]

                            country.setText(address.countryName)
                            city.setText(address.locality)

                        } else {
                            Toast.makeText(context, "Couldn't get your Location", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Couldn't get your Location", Toast.LENGTH_LONG).show()
                }
        }
    }
}