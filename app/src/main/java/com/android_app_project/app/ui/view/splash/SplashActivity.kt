package com.android_app_project.app.ui.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.ui.view.login.LoginActivity

class SplashActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                LoginActivity.getStartIntent(
                    this
                )
            )
            finish()
        }, 5000)        // Durée de l'activité : 5 sec et lancement de la LoginActivity

    }

}