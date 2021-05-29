package com.android_app_project.app.ui.view.sensor.data_sensors

import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityBatteryBinding

class BatteryActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, BatteryActivity::class.java)
        }
    }

    private lateinit var binding: ActivityBatteryBinding

    // Declare button, that will show battery percentage when clicked
    private lateinit var textBattery: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battery)

        // Binding Activity
        binding = ActivityBatteryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Capteur de niveau de batterie
        textBattery = findViewById(R.id.valueBattery)
        // Call battery manager service
        val battery = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager

        // Get the battery percentage and store it in a INT variable
        val batteryLevel:Int = battery.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        // Display the variable using a Toast
        textBattery.setText(batteryLevel.toString() + " %")
        //Toast.makeText(applicationContext,"Battery is $batteryLevel%",Toast.LENGTH_LONG).show()

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