package com.android_app_project.app.ui.view.send_data

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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivitySendDataBinding
import com.android_app_project.app.ui.view.login.LocalPreferences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class SendDataActivity : AppCompatActivity(), SensorEventListener {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, SendDataActivity::class.java)
        }
    }
    private lateinit var binding: ActivitySendDataBinding

    private val myViewModel: SendDataViewModel by viewModel()

    //Classe représentant le manageur des capteurs et les différents capteurs
    private lateinit var sensorManager: SensorManager
    private var luminosite: Sensor? =null
    private var pressure: Sensor? =null
    private var temperature: Sensor? =null

    //Variables pour communiquer avec l'API
    private lateinit var sendluminosite : String
    private lateinit var sendbatterie : String
    private lateinit var sendpression : String
    private lateinit var sendtemperature : String
    private lateinit var sendgps : String

    // Init Position
    val PERMISSION_REQUEST_LOCATION = 9999
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_data)

        // Binding Activity
        binding = ActivitySendDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Appui sur le bouton refresh -> Récupération des informations des capteurs
        binding.refreshButton.setOnClickListener {
            //Récupération de l'user_ID stocker dans le LocalPreferences lors du Login
            val user_id = LocalPreferences.getInstance(this).getSaveStringValue()
            binding.txtUserId.text = user_id

            // Ecoute des events provenants du capteur de luminosite
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            luminosite = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            if (luminosite != null) {
                // Success! There's a light sensor.
                sensorManager.registerListener(this, luminosite, SensorManager.SENSOR_DELAY_NORMAL)
            } else {
                // Failure! No light sensor.
                sendluminosite="NA"
                binding.txtLight.text = sendluminosite

            }

            //  Ecoute des events provenants du capteur de Pression
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
            if (pressure != null) {
                // Success! There's a light sensor.
                sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL)
            } else {
                // Failure! No light sensor.
                sendpression="NA"
                binding.txtPressure.text = sendpression
            }

            //  Ecoute des events provenants du capteur de Temperature ambiante
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            if (temperature != null) {
                // Success! There's a light sensor.
                sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
            } else {
                // Failure! No light sensor.
                sendtemperature="NA"
                binding.txtTemperature.text = sendtemperature
            }

            // Récupération du niveau de batterie
            val battery = applicationContext.getSystemService(BATTERY_SERVICE) as BatteryManager
            // Get the battery percentage and store it in a INT variable
            val batteryLevel:Int = battery.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            sendbatterie=batteryLevel.toString()
            binding.txtBattery.setText(sendbatterie + " %")

            // Récupération de la possition Position
            requestPermission()
            //Le bouton d'envoi d'information est désactiver de base, il est active que si on a déja collecter les informations
            binding.collectButton.setEnabled(true)
        }
        //Envoi des données collecter vers le serveur via API, l'envoi est désactiver jusqu'a une nouvelle collecte
        binding.collectButton.setOnClickListener {
            //La collection des données GPS prend quelques secondes la première fois / Attente de la collecte ou du refus de la permission
            if (binding.txtPosition.text!=""){

                myViewModel.sendData(binding.txtUserId.text.toString().toInt(),
                        sendluminosite,
                        sendbatterie,
                        sendpression,
                        sendtemperature,
                        sendgps)
                binding.collectButton.setEnabled(false)
                Toast.makeText(this, getString(R.string.dataSentMsg), Toast.LENGTH_SHORT).show()
            }
            else{
                //Message indicant l'attente des données GPS
                Toast.makeText(this, getString(R.string.waiting_for_gps_msg), Toast.LENGTH_SHORT).show()
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
            // Affichage Luminosité
            if (event.sensor.type == Sensor.TYPE_LIGHT) {
                sendluminosite=event.values[0].toString()
                binding.txtLight.text = sendluminosite + " Lux"
                sensorManager.unregisterListener(this,luminosite)
            }
            // Affichage Pression
            if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                sendpression = event.values[0].toString()
                binding.txtPressure.text = sendpression + " hPa"
                sensorManager.unregisterListener(this,pressure)
            }
            // Affichage Temperature ambiante
            if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                sendtemperature = event.values[0].toString()
                binding.txtTemperature.text = sendtemperature + " °C"
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
    // Demande de la permission de géolocalisation
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
    // Fonction qui si oui ou non on a la permission
    private fun hasPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    // Fonction qui retourne le résultat de la demande de permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission obtenue, Nous continuons la suite de la logique.
                    getLocation()
                } else {
                    // Permission non accepté
                    binding.txtPosition.text="NA"
                }
                return
            }
        }
    }
    //Récupération du location et appel du geoCodeur si permission ok
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (hasPermission()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, CancellationTokenSource().token)
                .addOnSuccessListener { geoCode(it) }
                .addOnFailureListener {
                    // Remplacer par un vrai bon message
                    Toast.makeText(this, getString(R.string.cant_localize), Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Récupération de la longitude et latitude et écriture des information sur l'activity
    private fun geoCode(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        val results = geocoder.getFromLocation(location.latitude, location.longitude, 1)

        if (results.isNotEmpty()) {
            sendgps = String.format("%.2f",location.latitude)+"I"+String.format("%.2f",location.longitude)
            sendgps=sendgps.replace(",",".")
            binding.txtPosition.text = String.format("%.2f",location.latitude)+" / "+String.format("%.2f",location.longitude)
        }
    }


}