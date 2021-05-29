package com.android_app_project.app.ui.view.sensor.data_sensors

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityTemperatureBinding
import kotlinx.android.synthetic.main.activity_temperature.*

class TemperatureActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, TemperatureActivity::class.java)
        }
    }

    private lateinit var binding: ActivityTemperatureBinding

    private lateinit var sensorManager: SensorManager
    private var temperature: Sensor? =null
    private var isTemperatureSensorAvailable: Boolean? = null
    private lateinit var textTemperature: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)

        // Binding Activity
        binding = ActivityTemperatureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Capteur de temperature
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        textTemperature = findViewById(R.id.valueTemperature)

        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            // Success! There's a temperature sensor.
            temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            isTemperatureSensorAvailable = true
        } else {
            // Failure! No temperature sensor.
            valueTemperature.setText("Temperature non disponible")
        }

        // Activation de l'action retour dans la Toolbar de cette activity
        supportActionBar?.apply {
            setTitle("Paramères")
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowCustomEnabled(true)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TODO("Not yet implemented")
    }

    override fun onSensorChanged(event: SensorEvent) {
        var unitOfTemperature: Float = event.values[0]
        // Do something with this sensor data.
        textTemperature.setText(unitOfTemperature.toString() + " °C")
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        if(isTemperatureSensorAvailable==true) {
            sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        // Register a listener for the sensor.
        super.onPause()
        if(isTemperatureSensorAvailable==true) {
            sensorManager.unregisterListener(this,temperature)
        }
    }

    // Activation Bouton Retour
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}