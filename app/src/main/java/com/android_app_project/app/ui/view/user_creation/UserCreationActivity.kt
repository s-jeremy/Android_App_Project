package com.android_app_project.app.ui.view.user_creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityUserCreationBinding
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.login.LoginActivity
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


        // Lancement de l'activity Login après un clic sur le bouton Retour
        binding.buttonBack.setOnClickListener {
            startActivity(LoginActivity.getStartIntent(this))
            onBackPressed()

        }
        //Boutton de lancement de l'API de création d'utilisateur
        binding.buttonValidateCreation.setOnClickListener {
            //Transmission des données vers l'API
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

    //Récupération du résultat de l'API et affichage message
    private fun showInformation(status: String) {
        if(status == "0"){
            Toast.makeText(this, getString(R.string.signupDoneMsg), Toast.LENGTH_SHORT).show()
            startActivity(LoginActivity.getStartIntent(this))
        }
        else{
            Toast.makeText(this, getString(R.string.signupError_msg), Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_from_left,R.anim.anim_slide_to_right)
    }
}