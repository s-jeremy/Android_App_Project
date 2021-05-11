package com.android_app_project.app.ui.view.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityLoginBinding
import com.android_app_project.app.ui.view.user_creation.UserCreationActivity
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
        }

    }
}