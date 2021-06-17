package com.android_app_project.app.ui.view.collected_data

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android_app_project.app.R
import com.android_app_project.app.databinding.ActivityCollectedDataBinding
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
    private lateinit var binding: ActivityCollectedDataBinding

    private val myViewModel: CollectedDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collected_data)

        // Binding de la vue
        binding = ActivityCollectedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.collectButton.setOnClickListener {
            Log.d("Affichage Data","Afficher Data")
            myViewModel.collecteData(binding.userId.text.toString().toInt())
        }


        myViewModel.states.observe(this, Observer { state ->
            when(state){
                is CollectedDataViewModel.CollecteDataResult -> showInformation(state.status)
            }
        })

    }

    private fun showInformation(status: String) {
        Log.d("Resultat Récupération:", status)
        if(status == "NOT-FOUND"){
            Toast.makeText(this, "L'utilisateur n'a pas était trouver.", Toast.LENGTH_SHORT).show()
        }
        else{
            //Ecriture dans les TextView dédiés
            binding.txtLight.text="XXX"
            binding.txtBattery.text="XXX"
            binding.txtPressure.text="XXX"
            binding.txtTemperature.text="XXX"
            binding.txtPosition.text="XXX"
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_from_left,R.anim.anim_slide_to_right)
    }
}