package com.android_app_project.app.ui.view.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.ui.view.*
import com.android_app_project.app.ui.view.info.InfoActivity
import com.android_app_project.app.ui.view.introduction.IntroductionActivity
import com.android_app_project.app.ui.view.login.LoginActivity
import com.android_app_project.app.ui.view.sensor.SensorRecyclerViewActivity
import com.android_app_project.app.ui.view.sensor.data_sensors.TemperatureActivity
import com.android_app_project.app.ui.view.user_creation.UserCreationActivity
import com.hitomi.cmlibrary.CircleMenu
import com.hitomi.cmlibrary.OnMenuSelectedListener
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, MainActivity::class.java)
        }
    }

    private val myViewModel: MainViewModel by viewModel()

    private lateinit var circleMenu : CircleMenu
    private lateinit var constraintLayout : ConstraintLayout

    var arrList = arrayOf("0","1","2","3","4")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUi()
    }

    private fun setupUi() {
        myViewModel.states.observe(this, Observer { state ->
            when (state) {
                is Loading -> showLoader(true)
                is MainViewModel.CallResult -> showLoader(false, state.data)
                is Failed -> showError(state.error)
            }
        })

        circleMenu()
    }

    private fun circleMenu() {
        circleMenu = findViewById(R.id.circle_menu)
        constraintLayout = findViewById(R.id.constraint_layout)
        //var index: Int = 0

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.icon_menu,R.drawable.icon_cancel)
            circleMenu.addSubMenu(Color.parseColor("#88BEF5"),R.drawable.icon_people_info)
            circleMenu.addSubMenu(Color.parseColor("#83E85A"),R.drawable.icon_history)
            circleMenu.addSubMenu(Color.parseColor("#FFB432"),R.drawable.icon_info)
            circleMenu.addSubMenu(Color.parseColor("#BA53DE"),R.drawable.icon_wifi)
            circleMenu.addSubMenu(Color.parseColor("#FF8A5C"),R.drawable.icon_data_sensors)

            circleMenu.setOnMenuSelectedListener {
                    index -> Toast.makeText(this,"Selection: "+ arrList[index],Toast.LENGTH_SHORT).show();

                    when(arrList[index]) {
                        "0" -> {
                            val intentTemperature = Intent(this,TemperatureActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#ECFFFB"))
                            //Thread.sleep(3000L)
                            startActivity(intentTemperature)
                            finish()
                        }

                        "1" -> {
                            val intentRecycler = Intent(this,SensorRecyclerViewActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#96F7D2"))
                            startActivity(intentRecycler)
                            finish()
                        }

                        "2" -> {
                            val intentMain1 = Intent(this,MainActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#FACA42"))
                            startActivity(intentMain1)
                        }

                        "3" -> {
                            val intentMain2 = Intent(this,MainActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#D3CDE6"))
                            startActivity(intentMain2)
                        }

                        "4" -> {
                            val intentMain3 = Intent(this,MainActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#FFF591"))
                            startActivity(intentMain3)
                        }
                    }
            }
    }

    private fun showError(error: Throwable) {
        AlertDialog
            .Builder(this)
            .setTitle("Error")
            .setMessage(error.message)
            .create()
            .show()
    }

    private fun showLoader(state: Boolean, receivedData: String = "") {
        if (state) {
            /*findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnRemoteAction).visibility = View.GONE
            findViewById<Button>(R.id.btnLocalAction).visibility = View.GONE*/
        } else {
            /*Toast.makeText(this, receivedData, Toast.LENGTH_SHORT).show()
            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
            findViewById<Button>(R.id.btnRemoteAction).visibility = View.VISIBLE
            findViewById<Button>(R.id.btnLocalAction).visibility = View.VISIBLE*/
        }
    }
}
