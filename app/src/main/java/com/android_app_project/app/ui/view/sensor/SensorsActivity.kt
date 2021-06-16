package com.android_app_project.app.ui.view.sensor

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivitySensorsBinding
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
    private var pressure: Sensor? =null
    private var isPressureSensorAvailable: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)

        // Binding Activity
        binding = ActivitySensorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pressure
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

        sensorManager.registerListener(this,pressure,SensorManager.SENSOR_DELAY_NORMAL)

        if (pressure == null) {
            // Success! There's a pressure sensor.
            binding.txtPressure.setText("Pression non disponible")
        }

        // Activation de l'action retour dans la Toolbar de cette activity
        supportActionBar?.apply {
            setTitle("Données")
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
            if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                binding.txtPressure.setText(event.values[0].toString() + " hPa")
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }


}