package com.android_app_project.app.ui.view.user_creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.app.AppCompatActivity
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityUserCreationBinding
import com.android_app_project.app.ui.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_user_creation.*
import org.koin.android.viewmodel.ext.android.viewModel

class UserCreationActivity : AppCompatActivity()  {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, UserCreationActivity::class.java)
        }
    }

    private lateinit var binding: ActivityUserCreationBinding

    private val myViewModel: UserCreationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_creation)

        // Binding de la vue
        binding = ActivityUserCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        password.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.buttonShow.setOnClickListener {
            if(buttonShow.text.toString().equals("Montrer")){
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.buttonShow.text = "Cacher"
            } else{
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.buttonShow.text = "Montrer"
            }
        }

        binding.buttonBack.setOnClickListener {
            startActivity(LoginActivity.getStartIntent(this))
        }

    }
}