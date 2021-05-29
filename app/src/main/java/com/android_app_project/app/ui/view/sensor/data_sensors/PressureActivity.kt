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
import com.android_app_project.app.databinding.ActivityPressureBinding
import kotlinx.android.synthetic.main.activity_pressure.*

class PressureActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, PressureActivity::class.java)
        }
    }

    private lateinit var binding: ActivityPressureBinding

    private lateinit var sensorManager: SensorManager
    private var pressure: Sensor? =null
    private var isPressureSensorAvailable: Boolean? = null
    private lateinit var textPressure: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pressure)

        // Binding Activity
        binding = ActivityPressureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Capteur de pression
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        textPressure = findViewById(R.id.valuePressure)

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            // Success! There's a pressure sensor.
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
            isPressureSensorAvailable = true
        } else {
            // Failure! No pressure sensor.
            valuePressure.setText("Pression non disponible")
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
        var unitOfPressure: Float = event.values[0]
        // Do something with this sensor data.
        textPressure.setText(unitOfPressure.toString() + " hPa")
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        if(isPressureSensorAvailable==true) {
            sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        // Register a listener for the sensor.
        super.onPause()
        if(isPressureSensorAvailable==true) {
            sensorManager.unregisterListener(this,pressure)
        }
    }

    // Activation Bouton Retour
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}