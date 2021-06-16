package com.android_app_project.app.ui.view.sensor

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityPositionBinding
import com.android_app_project.app.databinding.ActivitySensorsBinding
import com.android_app_project.app.ui.view.login.LocalPreferences
import com.android_app_project.app.ui.view.sensor.data_sensors.PressureActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.android.synthetic.main.activity_pressure.*
import java.util.*

class SensorsActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, SensorsActivity::class.java)
        }
    }
    private lateinit var binding: ActivitySensorsBinding

    private lateinit var sensorManager: SensorManager
    private var luminosite: Sensor? =null
    private var pressure: Sensor? =null
    private var temperature: Sensor? =null
    // Init Position
    val PERMISSION_REQUEST_LOCATION = 9999
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)

        // Binding Activity
        binding = ActivitySensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Appui sur le bouton refresh
        binding.refreshButton.setOnClickListener {

            val user_id = LocalPreferences.getInstance(this).getSaveStringValue()
            binding.txtUserId.text = user_id

            // Luminosite
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            luminosite = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            if (luminosite != null) {
                // Success! There's a light sensor.
                sensorManager.registerListener(this, luminosite, SensorManager.SENSOR_DELAY_NORMAL)
            } else {
                // Failure! No light sensor.
                binding.txtLight.text = "N/A"
            }

            // Pression
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
            if (pressure != null) {
                // Success! There's a light sensor.
                sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL)
            } else {
                // Failure! No light sensor.
                binding.txtPressure.text = "N/A"
            }

            // Temperature ambiante
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            if (temperature != null) {
                // Success! There's a light sensor.
                sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
            } else {
                // Failure! No light sensor.
                binding.txtTemperature.text = "N/A"
            }

            // Batterie
            val battery = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
            // Get the battery percentage and store it in a INT variable
            val batteryLevel:Int = battery.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            // Display the variable using a Toast
            binding.txtBattery.setText(batteryLevel.toString() + " %")

            // Position
            requestPermission()

        }

        // Activation de l'action retour dans la Toolbar de cette activity
        supportActionBar?.apply {
            title = "Données"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowCustomEnabled(true)
        }
    }

    // Activation Bouton Retour
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            // Luminosité
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                binding.txtLight.text = event.values[0].toString() + " Lux"
                sensorManager.unregisterListener(this,luminosite)
            }
            // Pression
            if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                binding.txtPressure.text = event.values[0].toString() + " hPa"
                sensorManager.unregisterListener(this,pressure)
            }
            // Temperature ambiante
            if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                binding.txtTemperature.text = event.values[0].toString() + " °C"
                sensorManager.unregisterListener(this,temperature)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // TODO
    }

    //-------------------------------------------------
    //----------------Methodes Position----------------
    //-------------------------------------------------

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
            binding.txtPosition.text = String.format("%.2f",location.latitude)+" - "+String.format("%.2f",location.longitude)
        }
    }


}