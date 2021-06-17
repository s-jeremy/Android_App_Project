package com.android_app_project.app.ui.view.introduction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.android_app_project.app.R
import kotlinx.android.synthetic.main.activity_introduction.*

//Activity qui permet de contrôler les fragment affichant les informations du projet
class IntroductionActivity: AppCompatActivity()  {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, IntroductionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        setUpNavigation()
    }

    fun setUpNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(bottom_navigation, navHostFragment!!.navController)
    }

}