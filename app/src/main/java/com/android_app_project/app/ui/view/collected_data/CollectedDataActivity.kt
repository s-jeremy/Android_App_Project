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
import com.android_app_project.app.ui.view.Failed
import com.android_app_project.app.ui.view.user_creation.UserCreationViewModel
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
            if (!binding.userId.text.isNullOrEmpty()){
                myViewModel.collecteData(binding.userId.text.toString().toInt())
            }
            else{
                Toast.makeText(this, "User Id Vide", Toast.LENGTH_SHORT).show()
            }
        }

        myViewModel.states.observe(this, Observer { state ->
            when(state){
                is CollectedDataViewModel.GetCollectDataResult -> showInformation(state.status)
                is Failed -> finish()
            }
        })

    }

    private fun showInformation(status: Array<String>) {
        if(status[0] == "NOT-FOUND"){
            Toast.makeText(this, "L'utilisateur n'a pas était trouver.", Toast.LENGTH_SHORT).show()
        }
        else{
            //Ecriture dans les TextView dédiés
            binding.txtUserId.text=binding.userId.text
            if (status[0]=="NA"){
                binding.txtLight.text=status[0]
            }
            else {
                binding.txtLight.text = status[0] + " lux"
            }
            binding.txtBattery.text=status[1]+"%"
            if (status[2]=="NA") {
                binding.txtPressure.text = status[2]
            }
            else{
                binding.txtPressure.text = status[2] + " hPa"
            }
            if (status[2]=="NA") {
                binding.txtTemperature.text = status[3]
            }
            else{
                binding.txtTemperature.text = status[3] + " °C"
            }
            binding.txtPosition.text=status[4].replace("I"," / ")
        }
    }

    // Activation Bouton Retour
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_slide_from_left,R.anim.anim_slide_to_right)
    }
}