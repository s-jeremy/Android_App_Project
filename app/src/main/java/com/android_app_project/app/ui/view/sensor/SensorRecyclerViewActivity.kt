package com.android_app_project.app.ui.view.sensor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivitySensorRecyclerViewBinding
import com.android_app_project.app.ui.view.main.MainActivity
import com.android_app_project.app.ui.view.sensor.data_sensors.BatteryActivity
import com.android_app_project.app.ui.view.sensor.data_sensors.LightActivity
import com.android_app_project.app.ui.view.sensor.data_sensors.PressureActivity
import com.android_app_project.app.ui.view.sensor.data_sensors.TemperatureActivity

class SensorRecyclerViewActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, SensorRecyclerViewActivity::class.java)
        }
    }

    private lateinit var binding: ActivitySensorRecyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_recycler_view)
        // Ouvre une application email à partir une fonction Intent
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("some@email.address"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
        intent.putExtra(Intent.EXTRA_TEXT, "mail body")

        // Ouvre les paramètres de l'application
        // Retourne une variable i correspondant à l'activité de l'item
        val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        i.addCategory(Intent.CATEGORY_DEFAULT)
        i.data = Uri.parse("package:" + "com.example.android_project")

        // Binding Activity
        binding = ActivitySensorRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerSensors.layoutManager = LinearLayoutManager(this)
        binding.recyclerSensors.adapter =
                // Déclaration dans la datasource sous la forme d'un tableau
            AdapterSensor(
                arrayOf(
                    // Temperature ambiante
                    SensorItem(
                        "Temperature ambiante",
                        R.drawable.icon_data_action
                    ) {
                        startActivity(TemperatureActivity.getStartIntent(this))
                    },
                    // Pression
                    SensorItem(
                        "Pression",
                        R.drawable.icon_data_action
                    ) {
                        startActivity(PressureActivity.getStartIntent(this))
                    },
                    // Luminosité ambiante
                    SensorItem(
                        "Luminosité ambiante",
                        R.drawable.icon_data_action
                    ) {
                        startActivity(LightActivity.getStartIntent(this))
                    },
                    // Niveau de batterie
                    SensorItem(
                        "Niveau de batterie",
                        R.drawable.icon_data_action
                    ) {
                        startActivity(BatteryActivity.getStartIntent(this))
                    },
                    // Position GPS
                    // Missing for the moment
                    SensorItem(
                        "Position GPS",
                        R.drawable.icon_data_action
                    ) {
                        startActivity(MainActivity.getStartIntent(this))
                    }
                ))

        // Activation de l'action retour dans la Toolbar de cette activity
        supportActionBar?.apply {
            setTitle("Paramères")
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowCustomEnabled(true)
        }
    }

    // Activation Bouton Retour
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
