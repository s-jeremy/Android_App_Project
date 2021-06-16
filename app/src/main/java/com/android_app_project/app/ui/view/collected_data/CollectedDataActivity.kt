package com.android_app_project.app.ui.view.collected_data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityUserCreationBinding
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class CollectedDataActivity : AppCompatActivity()  {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, CollectedDataActivity::class.java)
        }
    }
    //TODO("A MODIFIER QUAND LA VUE EST CREE")
    private lateinit var binding: ActivityUserCreationBinding

    private val myViewModel: CollectedDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO("A MODIFIER QUAND LA VUE EST CREE")
        setContentView(R.layout.activity_user_creation)

        // Binding de la vue
        //TODO("A MODIFIER QUAND LA VUE EST CREE")
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
            //TODO("VOIR VERS QUELLE PAGE ON RETOURNE")
            startActivity(LoginActivity.getStartIntent(this))
            onBackPressed()

        }
        //TODO("FINIR QUAND BINDING OK")
        /*binding.buttonCollecte.setOnClickListener {
            Log.d("Affichage Data","Afficher Data")
            //myViewModel.collecteData(binding.id_user.text.toString().toInt())
        }*/

        myViewModel.states.observe(this, Observer { state ->
            when(state){
                is CollectedDataViewModel.CollecteDataResult -> showInformation(state.status)
                is Failed -> finish()
            }
        })

    }

    private fun showInformation(status: String) {
        Log.d("Resultat du Signup:", status)
        if(status == "NOT-FOUND"){
            Toast.makeText(this, "L'utilisateur n'a pas était trouver.", Toast.LENGTH_SHORT).show()
        }
        else{
            //TODO("A FINIR QUAND VUE OK")
            //Ecriture dans les TextView dédiés
            //binding.luminosite.text="XXX"
            //binding.batterie.text="XXX"
            //binding.pression.text="XXX"
            //binding.temperature.text="XXX"
            //binding.gps.text="XXX"
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_from_left,R.anim.anim_slide_to_right)
    }
}