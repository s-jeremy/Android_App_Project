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
import com.android_app_project.app.databinding.ActivityLightBinding
import com.android_app_project.app.databinding.ActivityPressureBinding
import kotlinx.android.synthetic.main.activity_pressure.*

class LightActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, LightActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLightBinding

    private lateinit var sensorManager: SensorManager
    private var light: Sensor? =null
    private var isLightSensorAvailable: Boolean? = null
    private lateinit var textLight: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)

        // Binding Activity
        binding = ActivityLightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Capteur de luminosité ambiante
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        textLight = findViewById(R.id.valueLight)

        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            // Success! There's a light sensor.
            light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            isLightSensorAvailable = true
        } else {
            // Failure! No light sensor.
            valuePressure.setText("Luminosité ambiante non disponible")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        var unitOfLight: Float = event.values[0]
        // Do something with this sensor data.
        textLight.setText(unitOfLight.toString() + " lux")
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        if(isLightSensorAvailable==true) {
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Activation de l'action retour dans la Toolbar de cette activity
        supportActionBar?.apply {
            setTitle("Paramères")
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowCustomEnabled(true)
        }
    }

    override fun onPause() {
        // Register a listener for the sensor.
        super.onPause()
        if(isLightSensorAvailable==true) {
            sensorManager.unregisterListener(this,light)
        }
    }

    // Activation Bouton Retour
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}