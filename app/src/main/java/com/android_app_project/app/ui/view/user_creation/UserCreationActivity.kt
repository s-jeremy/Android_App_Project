package com.android_app_project.app.ui.view.user_creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityUserCreationBinding
import com.android_app_project.app.ui.di.moduleApp
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.login.LoginActivity
import kotlinx.android.synthetic.main.activity_user_creation.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

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

        // Gestion de l'affichage du mot de passe
        /*password.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.buttonShow.setOnClickListener {
            if(buttonShow.text.toString().equals("Montrer")){
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.buttonShow.text = "Cacher"
            } else{
                password.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.buttonShow.text = "Montrer"
            }
        }*/

        // Lancement de l'activity Login après un clic sur le bouton Retour
        binding.buttonBack.setOnClickListener {
            startActivity(LoginActivity.getStartIntent(this))
            onBackPressed()

        }

        binding.buttonValidateCreation.setOnClickListener {
            Log.d("Création User","Création User")
            myViewModel.createUser(
                    binding.username.text.toString(),
                    binding.password.text.toString(),
                    binding.email.text.toString(),
                    binding.firstName.text.toString(),
                    binding.lastName.text.toString())
        }

        myViewModel.states.observe(this, Observer { state ->
            when(state){
                is UserCreationViewModel.GetUserInformationResult -> showInformation(state.status.toString())
                is Failed -> finish()
            }
        })

    }

    private fun showInformation(status: String) {
        Log.d("Resultat du Signup:", status)
        if(status == "0"){
            Toast.makeText(this, "Votre compte à bien était crée.", Toast.LENGTH_SHORT).show()
            startActivity(LoginActivity.getStartIntent(this))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_from_left,R.anim.anim_slide_to_right)
    }
}