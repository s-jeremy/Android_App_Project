package com.android_app_project.app.ui.view.sensor.data_sensors

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityPositionBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import java.util.*

class PositionActivity: AppCompatActivity() {
    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, PositionActivity::class.java)
        }
    }

    val PERMISSION_REQUEST_LOCATION = 9999
    private lateinit var binding: ActivityPositionBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_position)

        binding = ActivityPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setTitle("Où suis-je ?")
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowCustomEnabled(true)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.locationButton.setOnClickListener {
            requestPermission()
        }

    }

    private fun position(location: Location) {
        binding.locationText.setText(location.latitude.toString() + ","+ location.longitude.toString());
    }

    private fun requestPermission() {
        if (!hasPermission()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_LOCATION)
        } else {
            getLocation()
        }
    }

    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    // TODO
                    // Permission non accepté, expliqué ici via une activité ou une dialog pourquoi nous avons besoin de la permission
                }
                return
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {                   // Activation Bouton Retour
        finish()
        return true
    }

    //Add to comment !!!!!!
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (hasPermission()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                .addOnSuccessListener { geoCode(it) }
                .addOnFailureListener {
                    // Remplacer par un vrai bon message
                    Toast.makeText(this, "Localisation impossible", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun geoCode(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        if (results.isNotEmpty()) {
            //binding.locationText.text = results[0].getAddressLine(0)
            binding.locationText.text = location.latitude.toString()
            //this.position(location);
        }
    }

}