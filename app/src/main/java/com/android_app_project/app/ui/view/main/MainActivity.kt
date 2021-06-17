package com.android_app_project.app.ui.view.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityMainBinding
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.Loading
import com.android_app_project.app.ui.view.collected_data.CollectedDataActivity
import com.android_app_project.app.ui.view.help.HelpActivity
import com.android_app_project.app.ui.view.introduction.IntroductionActivity
import com.android_app_project.app.ui.view.login.LoginActivity
import com.android_app_project.app.ui.view.send_data.SendDataActivity
import com.hitomi.cmlibrary.CircleMenu
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, MainActivity::class.java)
        }
    }

    private val myViewModel: MainViewModel by viewModel()

    private lateinit var circleMenu : CircleMenu
    private lateinit var constraintLayout : ConstraintLayout
    private lateinit var binding: ActivityMainBinding
    //Tableau représenter le nombre de sous-menus
    var arrList = arrayOf("0","1","2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Binding de la vue
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUi()

        logout()

        helpMain()
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

    //Fonction qui gère les animations et actions du menu en cercle
    private fun circleMenu() {
        //Récupération du Menu cercle
        circleMenu = findViewById(R.id.circle_menu)
        constraintLayout = findViewById(R.id.constraint_layout)

        //setMainMenu = bouton d'ouverture du menu
        //addSubMenu = 3 sous menus
        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.icon_menu,R.drawable.icon_cancel)
            circleMenu.addSubMenu(Color.parseColor("#88BEF5"),R.drawable.icon_people_info)
            circleMenu.addSubMenu(Color.parseColor("#83E85A"),R.drawable.icon_history)
            circleMenu.addSubMenu(Color.parseColor("#FFB432"),R.drawable.icon_info)

            //Actions des sous-menus (Selection de la page vers laquelle on navigue)
            circleMenu.setOnMenuSelectedListener {
                    index ->
                    when(arrList[index]) {
                        "0" -> {
                            val intentTemperature = Intent(this,SendDataActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#ECFFFB"))
                            startActivity(intentTemperature)
                        }

                        "1" -> {
                            val intentRecycler = Intent(this,CollectedDataActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#96F7D2"))
                            startActivity(intentRecycler)
                        }

                        "2" -> {
                            val intentIntro = Intent(this, IntroductionActivity::class.java)
                            constraintLayout.setBackgroundColor(Color.parseColor("#FACA42"))
                            startActivity(intentIntro)
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
    }
    //Fonction de déconnexion : redirige vers la page de Login
    private fun logout() {
        binding.logoutAction.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Destroy MainActivity and not exist in Back stack
        }
    }

    //Transition vers la page d'aide
    private fun helpMain() {
        binding.helpAction.setOnClickListener {
            startActivity(HelpActivity.getStartIntent(this))
        }
    }
}
