package com.android_app_project.app.ui.view.send_data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityUserCreationBinding
import com.android_app_project.app.ui.view.Failed
import org.koin.android.viewmodel.ext.android.viewModel

class SendDataActivity2 : AppCompatActivity()  {

    companion object {
        fun getStartIntent(ctx: Context): Intent {
            return Intent(ctx, SendDataActivity2::class.java)
        }
    }
    //TODO("A MODIFIER QUAND LA VUE EST CREE")
    private lateinit var binding: ActivityUserCreationBinding

    private val myViewModel: SendDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO("A MODIFIER QUAND LA VUE EST CREE")
        setContentView(R.layout.activity_user_creation)

        // Binding de la vue
        //TODO("A MODIFIER QUAND LA VUE EST CREE")
        binding = ActivityUserCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lancement de l'activity Login après un clic sur le bouton Retour
        binding.buttonBack.setOnClickListener {
            //TODO("VOIR VERS QUELLE PAGE ON RETOURNE")
            onBackPressed()

        }
        //TODO("FINIR QUAND BINDING OK")
        /*binding.buttonSend.setOnClickListener {
            Log.d("Envoi des données","Envoi des données")
            val user_id = LocalPreferences.getInstance(this).getSaveStringValue()
            //myViewModel.sendData(id_user.text.toInt(),
             binding.luminosite.text.toString(),
             binding.batterie.text.toString(),
             binding.pression.text.toString(),
             binding.temperature.text.toString(),
             binding.gps.text.toString(),)
        }*/

        myViewModel.states.observe(this, Observer { state ->
            when(state){
                is SendDataViewModel.SendDataResult -> showInformation(state.status.toString())
                is Failed -> finish()
            }
        })

    }

    private fun showInformation(status: String) {
        Log.d("Résultat : 0 = OK : ", status)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_from_left,R.anim.anim_slide_to_right)
    }
}


