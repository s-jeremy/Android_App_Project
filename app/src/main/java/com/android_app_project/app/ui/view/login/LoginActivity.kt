package com.android_app_project.app.ui.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.data.remote.SampleRemoteDataSource
import com.android_app_project.app.databinding.ActivityLoginBinding
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.introduction.IntroductionActivity
import com.android_app_project.app.ui.view.main.MainActivity
import com.android_app_project.app.ui.view.user_creation.UserCreationActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity()  {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, LoginActivity::class.java)
        }
    }

    private lateinit var binding: ActivityLoginBinding

    private val myViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Binding de la vue
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCreateUser.setOnClickListener {
            startActivity(UserCreationActivity.getStartIntent(this))
            overridePendingTransition(R.anim.anim_slide_from_right, R.anim.anim_slide_to_left)
        }

        binding.buttonValidate.setOnClickListener {
            myViewModel.login(binding.userLogin.text.toString(), binding.passwordLogin.text.toString())
        }

        myViewModel.states.observe(this,Observer { state ->
            when(state){
                is LoginViewModel.LoginResult -> showInformation(state.status.toString())
                is Failed -> finish()
            }
        })

    }

    private fun showInformation(status: String) {
        Log.d("Resultat de Login: :", status)
        if(status == "0"){
            startActivity(IntroductionActivity.getStartIntent(this))
        }

    }

}