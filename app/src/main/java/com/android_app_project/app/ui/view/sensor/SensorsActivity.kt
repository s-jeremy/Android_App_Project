package com.android_app_project.app.ui.view.sensor

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivitySensorsBinding
import com.android_app_project.app.ui.view.login.LocalPreferences
import com.android_app_project.app.ui.view.sensor.data_sensors.PressureActivity
import kotlinx.android.synthetic.main.activity_pressure.*

class SensorsActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, PressureActivity::class.java)
        }
    }
    private lateinit var binding: ActivitySensorsBinding

    private lateinit var sensorManager: SensorManager
    private var luminosite: Sensor? =null
    private var isPressureSensorAvailable: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)

        // Binding Activity
        binding = ActivitySensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                binding.txtLight.text = "Luminosité ambiante non disponible"
            }
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
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                binding.txtLight.text = event.values[0].toString() + " Lux"
                sensorManager.unregisterListener(this,luminosite)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

}